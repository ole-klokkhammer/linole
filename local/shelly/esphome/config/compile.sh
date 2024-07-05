#!/bin/bash

source ./venv/bin/activate

device_name=$1

echo "Compiling the ESPHome configuration"
esphome compile "$device_name".yaml

output_folder=".esphome/build/$device_name/.pioenvs/$device_name"

echo "gzipping coz too big for OTA"
gzip -k $output_folder/firmware.bin

echo "Moving (and overwriting) to Downloads folder"
mv -f $output_folder/firmware.bin.gz /home/ole/Downloads/"$device_name".bin.gz