# HOWTO

- https://templates.blakadder.com/shelly_Dimmer_2.html
- https://esphome.io/components/light/shelly_dimmer.html
- https://templates.blakadder.com/shelly_Dimmer_2.html
- https://www.reddit.com/r/homeassistant/comments/p0z9oj/shelly_dimmer_2_tasmota_button_long_press/
- https://github.com/arendst/mgos-to-tasmota
- https://github.com/jamesturton/shelly-dimmer-stm32/releases

## Setup

- https://siytek.com/add-esphome-to-home-assistant/
- https://siytek.com/esp8266-web-server-led/#Installing_ESPHome

1. We first need to flash tasmota -
    - https://www.youtube.com/watch?v=_oRr8FZyyQ0
    - https://github.com/arendst/mgos-to-tasmota/blob/master/README.md
    - http://shellyip/ota?url=http://ota.tasmota.com/tasmota/shelly/mg2tasmota-ShellyDimmer2.zip
2. Configure Tasmota WIFI
    - Connect to the tasmota ap
    - Connect to local wifi
3. Install esphome commandline:
   - https://esphome.io/guides/installing_esphome.html
   - python3 -m venv venv
   - source venv/bin/activate
   - pip3 install esphome
   - export PATH=$PATH:$HOME/.local/bin ??
4. create config
   - esphome web_control.yaml wizard
5. migrate from tasmota
   - https://esphome.io/guides/migrate_sonoff_tasmota.html
   - esphome compile <device>.yaml
   - find the bin file (bin/<device>/.pionenvs/<device>/firmware.bin)
   - gzip -k firmware.bin
   - upload through tasmota web interface (gzip first coz storage)