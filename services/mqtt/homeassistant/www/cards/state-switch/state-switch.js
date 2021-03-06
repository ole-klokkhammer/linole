!function(t){var e={};function i(s){if(e[s])return e[s].exports;var r=e[s]={i:s,l:!1,exports:{}};return t[s].call(r.exports,r,r.exports,i),r.l=!0,r.exports}i.m=t,i.c=e,i.d=function(t,e,s){i.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:s})},i.r=function(t){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},i.t=function(t,e){if(1&e&&(t=i(t)),8&e)return t;if(4&e&&"object"==typeof t&&t&&t.__esModule)return t;var s=Object.create(null);if(i.r(s),Object.defineProperty(s,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var r in t)i.d(s,r,function(e){return t[e]}.bind(null,r));return s},i.n=function(t){var e=t&&t.__esModule?function(){return t.default}:function(){return t};return i.d(e,"a",e),e},i.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},i.p="",i(i.s=0)}([function(t,e,i){"use strict";i.r(e);const s=customElements.get("home-assistant-main")?Object.getPrototypeOf(customElements.get("home-assistant-main")):Object.getPrototypeOf(customElements.get("hui-view")),r=s.prototype.html,o=s.prototype.css;function a(){return document.querySelector("home-assistant").hass}function n(t,e,i=null){if((t=new Event(t,{bubbles:!0,cancelable:!1,composed:!0})).detail=e||{},i)i.dispatchEvent(t);else{var s=document.querySelector("home-assistant");(s=(s=(s=(s=(s=(s=(s=(s=(s=(s=(s=s&&s.shadowRoot)&&s.querySelector("home-assistant-main"))&&s.shadowRoot)&&s.querySelector("app-drawer-layout partial-panel-resolver"))&&s.shadowRoot||s)&&s.querySelector("ha-panel-lovelace"))&&s.shadowRoot)&&s.querySelector("hui-root"))&&s.shadowRoot)&&s.querySelector("ha-app-layout #view"))&&s.firstElementChild)&&s.dispatchEvent(t)}}const l="custom:";function c(t,e){const i=document.createElement("hui-error-card");return i.setConfig({type:"error",error:t,origConfig:e}),i}function d(t,e){if(!e||"object"!=typeof e||!e.type)return c(`No ${t} type configured`,e);let i=e.type;if(i=i.startsWith(l)?i.substr(l.length):`hui-${i}-${t}`,customElements.get(i))return function(t,e){const i=document.createElement(t);try{i.setConfig(e)}catch(t){return c(t,e)}return i}(i,e);const s=c(`Custom element doesn't exist: ${i}.`,e);s.style.display="None";const r=setTimeout(()=>{s.style.display=""},2e3);return customElements.whenDefined(i).then(()=>{clearTimeout(r),n("ll-rebuild",{},s)}),s}function u(t){return d("card",t)}let h=function(){if(window.fully&&"function"==typeof fully.getDeviceId)return fully.getDeviceId();if(!localStorage["lovelace-player-device-id"]){const t=()=>Math.floor(1e5*(1+Math.random())).toString(16).substring(1);localStorage["lovelace-player-device-id"]=`${t()}${t()}-${t()}${t()}`}return localStorage["lovelace-player-device-id"]}();customElements.define("state-switch",class extends s{static get properties(){return{hass:{},state:{}}}setConfig(t){this._config=t,this.state=void 0,this.cards={};for(let e in t.states)this.cards[e]=u(t.states[e]),this.cards[e].hass=a();if("hash"===t.entity&&window.addEventListener("location-changed",()=>this.updated(new Map)),"mediaquery"===t.entity)for(const t in this.cards)window.matchMedia(t).addEventListener("change",this.update_state.bind(this))}update_state(){let t=void 0;switch(this._config.entity){case"user":t=this.hass&&this.hass.user&&this.hass.user.name||void 0;break;case"group":t=this.hass&&this.hass.user&&this.hass.user.is_admin?"admin":"user";case"deviceID":case"browser":t=h;break;case"hash":t=location.hash.substr(1);break;case"mediaquery":for(const e in this.cards)if(window.matchMedia(e).matches){t=e;break}break;default:t=this.hass.states[this._config.entity],t=t?t.state:void 0}void 0!==t&&this.cards.hasOwnProperty(t)||(t=this._config.default),this.state=t}updated(t){if(t.has("hass"))for(let t in this.cards)this.cards[t].hass=this.hass;if(t.has("state")){const e=t.get("state");this.cards[e]&&(this.cards[e].classList.remove("visible"),this.cards[e].classList.add("out"),window.setTimeout(()=>{this.cards[e].classList.remove("out")},this._config.transition_time||500)),this.cards[this.state]&&this.cards[this.state].classList.add("visible")}else this.update_state()}render(){return r`
    <div
      id="root"
      class="${this._config.transition}"
      style="
        transition-duration: ${this._config.transition_time||500}ms;
        transition-delay: ${this._config.transition_time||500}ms;
        "
    >
      ${Object.keys(this.cards).map(t=>r`
          ${this.cards[t]}
        `)}
    </div>
    `}getCardSize(){let t=1;for(let e in this.cards)this.cards[e]&&this.cards[e].getCardSize&&(t=Math.max(t,this.cards[e].getCardSize()));return t}static get styles(){return o`
      :host {
        perspective: 1000px;
      }
      #root {
        display: grid;
      }
      #root * {
        display: none;
        grid-column: 1;
        grid-row: 1;
      }
      #root .visible {
        display: block;
      }


      #root.slide-right *,
      #root.slide-left * {
        display: block;
        opacity: 0;
        height: 0;
        transition-property: transform;
        transition-timing-function: linear;
        transition-duration: inherit;
        transform: translate(-110%);
      }
      #root.slide-left * {
        transform: translate(110%);
      }
      #root.slide-right .visible,
      #root.slide-left .visible {
        opacity: 1;
        height: auto;
        transform: translate(0%);
      }
      #root.slide-right .out,
      #root.slide-left .out {
        opacity: 1;
        height: auto;
        transform: translate(110%);
      }
      #root.slide-left .out {
        transform: translate(-110%);
      }


      #root.swap-right *,
      #root.swap-left * {
        display: block;
        opacity: 0;
        height: 0;
        transition-property: transform;
        transition-timing-function: linear;
        transition-duration: inherit;
        transform: translate(110%);
      }
      #root.swap-left *{
        transform: translate(-110%);
      }
      #root.swap-right .visible,
      #root.swap-left .visible {
        opacity: 1;
        height: auto;
        transition-delay: inherit;
        transform: translate(0%);
      }
      #root.swap-right .out,
      #root.swap-left .out {
        opacity: 1;
        height: auto;
      }


      #root.flip {
        width: 100%;
        height: 100%;
        position: relative;

      }
      #root.flip * {
        display: block;
        opacity: 0;
        height: 0;
        transform: rotateY(-180deg);
        transition-property: transform;
        transition-timing-function: linear;
        transition-duration: inherit;
        transform-style: preserve-3d;
        backface-visibility: hidden;
        z-index: 100;
      }
      #root.flip .visible {
        opacity: 1;
        height: auto;
        backface-visibility: hidden;
        transform: rotateY(0deg);
      }
      #root.flip .out {
        opacity: 1;
        height: auto;
        transform: rotateY(180deg);
      }
    `}}),customElements.whenDefined("hui-view").then(()=>{const t=customElements.get("hui-view").prototype,e=t.renderStyles;t.renderStyles=function(){let t=e();return t.strings=[t.strings[0]+"\n  <style>\n    .column {\n      overflow-y: hidden;\n    }\n  </style>\n  "],t},n("ll-rebuild",{})})}]);