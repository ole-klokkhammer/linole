#!/bin/sh
 
fangr1speed=$(echo $@ | awk '{print int($0*0.85)}')
fangr2speed=$(echo $@ | awk '{print int($0)}')

echo "Setting speed of fan1 and fan2 to" $fangr1speed
echo "Setting speed of fan3 to" $fangr2speed

cd liquidctl
sudo python3 -m liquidctl.cli --device 0 set fan1 speed $fangr1speed
sudo python3 -m liquidctl.cli --device 0 set fan2 speed $fangr1speed
sudo python3 -m liquidctl.cli --device 0 set fan3 speed $fangr2speed