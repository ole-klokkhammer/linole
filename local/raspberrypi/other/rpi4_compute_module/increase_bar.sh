#!/bin/bash

# The default BAR address space available on the CM4 may be too small to allow
# some devices to initialize correctly. To avoid 'failed to assign memory'
# errors on boot, you can increase the range of the PCIe bus in the Raspberry
# Pi's Device Tree (a .dtb file specific to each Pi model).
#
# You should probably read up on Device Trees if you don't know what they are:
# https://www.raspberrypi.org/documentation/configuration/device-tree.md
#
# NOTE: The default BAR allocation was increased to 1 GB in this commit:
# https://github.com/raspberrypi/linux/commit/54db4b2fa4d17251c2f6e639f849b27c3b553939

# Download the trial firmware from this post:
# https://www.raspberrypi.org/forums/viewtopic.php?p=1761834#p1761834
# and replace the corresponding files in the boot volume.

# Back up current Device Tree for the CM4.
sudo cp /boot/bcm2711-rpi-cm4.dtb /boot/bcm2711-rpi-cm4.dtb.bak

# Decompile the current Device Tree to a dts (source) file.
dtc -I dtb -O dts /boot/bcm2711-rpi-cm4.dtb -o ~/test.dts

# Edit the file and change the PCIe bus range as mentioned in:
# https://www.raspberrypi.org/forums/viewtopic.php?p=1746665#p1746665
nano ~/test.dts

# Replace the line that allocates 1 GB of RAM in the pci section:
#   ranges = <0x02000000 0x0 0xc0000000 0x6 0x00000000 0x0 0x40000000>;
#
# with the following line, which provides up to 8 GB of RAM:
#   ranges = <0x02000000 0x0 0x00000000 0x6 0x00000000 0x2 0x00000000>;

# Also replace the scb ranges to:
#    ranges = <0x0 0x7c000000  0x0 0xfc000000  0x0 0x03800000>,
#             <0x0 0x40000000  0x0 0xff800000  0x0 0x00800000>,
#             <0x6 0x00000000  0x6 0x00000000  0x2 0x00000000>,
#             <0x0 0x00000000  0x0 0x00000000  0x0 0xfc000000>;

# Recompile the Device Tree from the dts (source) file.
dtc -I dts -O dtb ~/test.dts -o ~/test.dtb

# Copy the new Device Tree into place.
sudo mv ~/test.dtb /boot/bcm2711-rpi-cm4.dtb

# Reboot.
sudo reboot