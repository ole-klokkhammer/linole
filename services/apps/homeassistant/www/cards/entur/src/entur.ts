/* eslint indent: "off" */
/* eslint no-undef: "off" */
import {
    LitElement,
    html,
    customElement,
    property,
    CSSResult,
    TemplateResult
} from 'lit-element';
import {
    HomeAssistant,
    handleClick
} from 'custom-card-helpers';

import moment from 'moment/src/moment';
import style from './style';
import 'moment/src/locale/nb';

import {EnturConfig} from "./types";
import {localize} from './localize/localize';

@customElement("entur-card")
class Entur extends LitElement {
    // Add any properities that should cause your element to re-render here.
    @property() public hass?: HomeAssistant;

    @property() private _config?: EnturConfig;

    public setConfig(config: EnturConfig): void {
        // TODO Check for required fields and that they are of the proper format
        if (!config || config.show_error) {
            throw new Error(localize('invalid_configuration'));
        }

        this._config = config;
    }

    protected render(): TemplateResult | void {
        if (['en', 'nb'].includes(this.hass.selectedLanguage)) {
            moment.locale(this.hass.selectedLanguage);
        } else {
            moment.locale('en');
        }

        if (!this._config || !this.hass) {
            return html``;
        }

        return html`
            <ha-card @ha-click="${this._handleTap}">  
                <div class="card-header entur-header">
                    <div class="entur-name">${this._config.name}</div>
                </div>
         
        ${this._config.entities.map((entity) => {
            const stateObj = this.hass.states[entity.entity];
            const station_name = entity.name ? entity.name : stateObj.attributes.friendly_name.match(/entur (.+?)(?= platform|$)/i)[1];
            const line = this.getLineInfo(stateObj);
            const next_line = this.getNextLineInfo(stateObj);
            const extraDepartures = this.getExtraDepartures(stateObj);

            return html` 
                        <div class="entur-item">  
                          <div class="entur-row">
                             <h2 class="entur-station">
                                ${station_name} 
                              </h2> 
                          </div>
                          <div class="entur-row">
                            <div class="entur-line"> 
                                <span>${line.route} ${moment(line.due_at, "HH:mm:ss").fromNow(false)}</span> 
                            </div>  
                          </div> 
                          <div class="entur-row">
                            <div class="entur-line">
                                <span>${next_line.route} ${moment(next_line.due_at, "HH:mm:ss").fromNow(false)}</span> 
                            </div>  
                          </div> 
                        </div>
                    `;
        })}
            </ha-card>
    `;
    }

    private getLineInfo(stateObj) {
        return {
            route: stateObj.attributes.route,
            delay: stateObj.attributes.delay,
            due_at: stateObj.attributes.due_at,
        };
    }

    private getNextLineInfo(stateObj) {
        return {
            route: stateObj.attributes.next_route,
            delay: stateObj.attributes.next_delay,
            due_at: stateObj.attributes.next_due_at,
        };
    }

    private getExtraDepartures(obj) {
        const extraDepartures = [];
        const getTime = /\b([01]\d|2[0-3]):[0-5]\d/g;

        // eslint-disable-next-line no-restricted-syntax
        for (const key of Object.keys(obj)) {
            if (key.startsWith('departure_')) {
                const time = obj[key].match(getTime);
                extraDepartures.push(
                    html`
            <div class="entur-row">
              <div class="entur-line">
                ${obj[key].replace(time, '').replace('ca. ', '')}
                <span class="entur-human is-now">${localize('arrives')} ${moment(time, "HH:mm").fromNow()}</span>
              </div>
              <div class="entur-status">
                <ha-icon class="entur-icon" icon="mdi:clock"></ha-icon>
                ${time}
              </div>
            </div>
          `
                );
            }
        }

        return extraDepartures;
    }

    private _handleTap(): void {
        handleClick(this, this.hass!, this._config!, false, false);
    }

    static get styles(): CSSResult {
        return style;
    }
}
