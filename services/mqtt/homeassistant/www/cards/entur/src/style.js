import {css} from 'lit-element';

const style = css`
    :host {
        --entur-primary-color: #44739e;
        --entur-line-color: var(--primary-text-color);
        --entur-station-color: rgba(128, 160, 200, .8);
        --entur-secondary-color: #9e9e9e; 
        --entur-mute-color: #efefef;
        --entur-warning-color: rgba(231, 76, 60, .8);
    }

    .warning {
        display: block;
        color: black;
        background-color: #fce588;
        padding: 8px;
    }

    .entur-header {
        display: flex;
    }

    .entur-name {
        flex: 1;
    }

    .entur-clock {
        align-self: flex-end;
    }

    .entur-item { 
        padding-left: 20px;
        padding-right: 20px;
        padding-bottom: 20px;
        align-items: start;
    }

    .entur-row { 
        display: flex;
        justify-content: space-between;
        padding-bottom: .8em;
    }

    .entur-type-icon {
        color: var(--entur-primary-color);
        align-self: center;
        width: 100%;
        height: auto;
    }

    .entur-station {
        color: var(--entur-station-color);
        font-size: 1.3em;
        font-weight: 300;
        margin: .3em 0;
    }

    .entur-line {
        color: var(--entur-line-color);
        font-size: 1.1em;
    }

    .entur-human {
        color: var(--entur-text-color);
        font-size: 0.8em;
        display: block;
        margin-top: .2em;
    }

    .entur-human.has-been {
        color: var(--entur-warning-color);
    }

    .entur-delay {
        color: var(--entur-warning-color);
        margin-left: auto;
        align-self: center;
    }

    .entur-status {
        color: var(--primary-color);
        margin-left: 1em;
        align-self: center;
    }

    .entur-icon {
        margin-top: -2px;
    }

    .entur-next {
        color: var(--entur-secondary-color);
        font-size: 0.8em;
        font-style: italic;
        margin-top: .5em;
    }

    .entur-next em {
        text-decoration: underline;
    }

    .entur-extra-departures {
        margin: .5em 0;
        padding: .5em 0;
        border-top: 1px dashed var(--entur-secondary-color);
    }

    .entur-extra-departure-line {
        color: var(--entur-secondary-color);
        line-height: 150%;
        font-size: 0.9em;
    }

    .entur--departure-status {
        color: var(--entur-secondary-color);
        line-height: 150%;
        font-size: 0.9em;
    }
    `;

export default style;