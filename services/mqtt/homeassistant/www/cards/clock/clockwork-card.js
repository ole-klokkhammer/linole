// Clockwork Card
// https://github.com/robmarkoski/ha-clockwork-card

class ClockWorkCard extends HTMLElement {
    
    constructor() {
        super();
        this.attachShadow({
            mode: 'open'
        });
    }

    /* This is called every time sensor is updated */
    set hass(hass) { 
        let _date_time;
        const config = this.config;
        const locale = config.locale;
        const _locale = locale ? locale : undefined;
        const entityId = config.entity;

        if (entityId) {
            const state = hass.states[entityId];
            const stateStr = state ? state.state : "Unavailable";
            if (stateStr === "Unavailable") {
                throw new Error("Sensor State Unavailable");
            }
            _date_time = new Date(stateStr);
        } else {
            _date_time = new Date();
        }


        if (_date_time === "Invalid Date") {
            throw new Error("Invalid date. Ensure its a ISO Date")
        }

        //Format the Time
        var _time = _date_time.toLocaleTimeString(_locale, {
            hour: 'numeric',
            minute: 'numeric'
        });

        //Format the Date
        var _date = _date_time.toLocaleDateString(_locale, {
            weekday: 'long',
            day: 'numeric',
            month: 'long'
        });

        /* console.log("Clock Contents: " + clock_contents);*/

        this.shadowRoot.getElementById('container').innerHTML = `
            <div class="clock">
                <div class="time" id="time">${_time}</div>
                <div class="date" id="date">${_date}</div>
            </div>
        `;
    }

    /* This is called only when config is updated */
    setConfig(config) { 
        const root = this.shadowRoot;
        if (root.lastChild) root.removeChild(root.lastChild);

        this.config = config;

        const card = document.createElement('ha-card');
        const content = document.createElement('div');
        const style = document.createElement('style')

        style.textContent = `
            .container {
                padding: 5px 5px 5px;
                display:flex;
                flex-flow: row wrap;
                justify-content: space-around;
                align-items: flex-start;
            }
            .clock {
                width: 100%;                
                padding: 5px 5px 5px 0px;
                margin: auto;
            }
            .other_clocks {
                float: right;
                margin: auto;
            }
            .otime {
                padding: 0px 5px 2px;
                font-size: 14px;
                font-family: var(--paper-font-headline_-_font-family);
                letter-spacing: var(--paper-font-headline_-_letter-spacing);
                text-rendering: var(--paper-font-common-expensive-kerning_-_text-rendering);
            }
            .tz_locale {
                padding: 0px 5px 1px;
                color: var(--secondary-text-color);
                font-size: 11px;
                font-family: var(--paper-font-headline_-_font-family);
                letter-spacing: var(--paper-font-headline_-_letter-spacing);
                text-rendering: var(--paper-font-common-expensive-kerning_-_text-rendering);
            }     
            .time {
                padding: 
                font-family: var(--paper-font-headline_-_font-family);
                -webkit-font-smoothing: var(--paper-font-headline_-_-webkit-font-smoothing);
                font-size: 56px;
                font-weight: var(--paper-font-headline_-_font-weight);
                letter-spacing: var(--paper-font-headline_-_letter-spacing);
                line-height: 1em;
                text-rendering: var(--paper-font-common-expensive-kerning_-_text-rendering);
		        text-align: center;
            }
            .date {
                font-family: var(--paper-font-headline_-_font-family);
                -webkit-font-smoothing: var(--paper-font-headline_-_-webkit-font-smoothing);
                font-size: 20px;
                font-weight: var(--paper-font-headline_-_font-weight);
                letter-spacing: var(--paper-font-headline_-_letter-spacing);
                line-height: var(--paper-font-headline_-_line-height);
                text-rendering: var(--paper-font-common-expensive-kerning_-_text-rendering);
                text-align: center;
            }          
        `;

        content.id = "container";
        content.className = "container";
        card.header = config.title;
        card.appendChild(style);
        card.appendChild(content);

        root.appendChild(card);
    }

    // The height of the card.
    getCardSize() {
        return 3;
    }
}

customElements.define('clockwork-card', ClockWorkCard);
