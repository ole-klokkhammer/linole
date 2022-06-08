import wol from "wake_on_lan";

export function wakeOnLan(mac: string): Promise<boolean> {
  return new Promise<boolean>(resolve => {
    wol.wake(mac, {num_packets: 30}, (error: Error) => {
      if (error) {
        console.error(error);
        resolve(false);
      } else {
        resolve(true);
      }
    });
  });
}
