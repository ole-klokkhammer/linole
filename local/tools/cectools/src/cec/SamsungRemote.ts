import {wakeOnLan} from './WakeOnLan';
import fetch from 'node-fetch';

export class SamsungRemote {
  ip: string;
  mac: string;
  pendingTurnOn: boolean = false;

  constructor(ip: string, mac: string) {
    this.ip = ip;
    this.mac = mac;
  }

  async isAvailable(): Promise<boolean> {
    try {
      const response = await fetch(`http://${this.ip}:8001/api/v2/'}`);
      return response.ok;
    } catch (error) {
      console.error(error);
      return false;
    }
  }

  async turnOn() {
    if (await this.isAvailable()) {
      console.log(`The tv should already be on.`);
    } else if (!this.pendingTurnOn) {
      console.log('Signalling to turn on the tv on wal...');
      this.pendingTurnOn = true;
      await wakeOnLan(this.mac);
      this.pendingTurnOn = false;
    } else {
      console.log(`Pending turn on is ${this.pendingTurnOn}!`);
    }
  }
}
