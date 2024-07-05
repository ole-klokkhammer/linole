# HOWTO

- https://templates.blakadder.com/shelly_Dimmer_2.html
- https://esphome.io/components/light/shelly_dimmer.html
- https://templates.blakadder.com/shelly_Dimmer_2.html
- https://www.reddit.com/r/homeassistant/comments/p0z9oj/shelly_dimmer_2_tasmota_button_long_press/
- https://github.com/arendst/mgos-to-tasmota
- https://github.com/jamesturton/shelly-dimmer-stm32/releases

## Steps
1. Flash Tasmota - 
    - https://www.youtube.com/watch?v=_oRr8FZyyQ0
    - https://github.com/arendst/mgos-to-tasmota/blob/master/README.md
    - http://shellyip/ota?url=http://ota.tasmota.com/tasmota/shelly/mg2tasmota-ShellyDimmer2.zip 
2. Configure Tasmota WIFI 
   - Connect to the tasmota ap
   - Connect to local wifi
3. Configure dimmer settings template:
   - https://templates.blakadder.com/shelly_Dimmer_2.html
4. Install shelly dimmer 2 firmware
   - https://github.com/jamesturton/shelly-dimmer-stm32/releases
5. Apply settings, rules etc.