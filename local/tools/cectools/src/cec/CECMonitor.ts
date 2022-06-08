import {CecMonitor as HdmiCecmonitor, Commander as HdmiRemote, LogicalAddress} from "hdmi-cec";
import {SamsungRemote} from "./SamsungRemote";
import {Config} from "../config";

enum CECEvent {
  ACTIVE_SOURCE = 'ACTIVE_SOURCE',
  REPORT_POWER_STATUS = 'REPORT_POWER_STATUS',
  ROUTING_CHANGE = 'ROUTING_CHANGE'
}

export class CECMonitor extends HdmiCecmonitor {
  hdmiRemote: HdmiRemote;
  wifiRemote: SamsungRemote;

  constructor() {
    super('volumiolivingroom', LogicalAddress.UNKNOWN, true, false);
    this.wifiRemote = new SamsungRemote(Config.ip, Config.mac);
    this.hdmiRemote = new HdmiRemote(this);
    this.addListeners();
  }

  private addListeners() {
    console.log('Adding cec monitor listeners...');
    this.once('ready', this.onReady.bind(this));
    this.on(CECEvent.REPORT_POWER_STATUS, this.onReportPowerStatus.bind(this));
    this.on(CECEvent.ACTIVE_SOURCE, this.onActiveSource.bind(this));
    this.on(CECEvent.ROUTING_CHANGE, this.onRoutingChange.bind(this));
    console.log('Done adding cec monitor listeners!');
  }

  start(clientName?: string, ...rest: string[]) {
    super.start(clientName, ...rest);
  }

  onReady(client: HdmiCecmonitor) {
    console.log(' -- READY -- ');
  }

  onReportPowerStatus(packet: any, status: any) {
    console.log(`Power status ${status}`);
  }

  onActiveSource(packet: any, source: any) {
    console.log(`Active source is now ${source}`);
    this.wifiRemote.turnOn().then(() => {
      console.log("Turned on the TV!");
    }).catch(error => {
      console.error(error);
    })
  }

  onRoutingChange(packet: any, source: any) {
    console.log(`Routing change ${source}`);
    this.wifiRemote.turnOn().then(() => {
      console.log("Turned on the TV!");
    }).catch(error => {
      console.error(error);
    });
  }
}
