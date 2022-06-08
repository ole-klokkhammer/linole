
# HOW TO CREATE THE NAS

1. connect usb and jumper to prevent boot
2. Attach the pi with usbboot
git clone --depth=1 https://github.com/raspberrypi/usbboot
cd usbboot
sudo apt install libusb-1.0-0-dev
make
sudo ./rpiboot
3. Install raspi os 64 bit?
https://downloads.raspberrypi.org/raspios_lite_arm64/images/raspios_lite_arm64-2021-05-28/
4. Enable ssh by adding a ssh file under the boot partition
5. Enable i2c
6. Overclock
https://www.jeffgeerling.com/blog/2020/overclocking-raspberry-pi-compute-module-4
arm_freq=2200
over_voltage=8
gpu_freq=500
7. Recompile the kernel with default values
https://github.com/geerlingguy/raspberry-pi-pcie-devices/issues/1#
This is easier done on the pi itself, but it takes a while.
8. Increase BAR to 1GB
https://gist.github.com/geerlingguy/9d78ea34cab8e18d71ee5954417429df
9. Format disk with fdisk
sudo fdisk /dev/sda
d 1    # delete partition 1
d 2    # delete partition 2
n    # create new partition
p    # primary (default)
1    # partition 1 (default)
2048    # First sector (default)
468862127    # Last sector (default)
w    # write new partition table
10. Reboot and format: sudo mkfs.ext4 /dev/sda1
11. Mount the disk
https://www.shellhacks.com/raspberry-pi-mount-usb-drive-automatically/
sudo mkdir /mnt/sata-sda
sudo mount /dev/sda1 /mnt/sata-sda
mount
ls -lt /mnt/sata-sda
Run the blkid command to find out the UUID of the USB drive:
Make a backup and open the /etc/fstab file in your favorite text editor:
sudo cp /etc/fstab /etc/fstab.back
Append a line to fstab:
sudo nano /etc/fstab
UUID=FC05-DF26 /mnt/sata-sda ext4 defaults,auto,users,rw 0 0
12. Move swap
https://www.howtoraspberry.com/2020/04/move-raspbian-swapfile-to-usb/
https://pimylifeup.com/raspberry-pi-swap-file/
sudo dphys-swapfile swapoff
sudo nano /etc/dphys-swapfile
$ CONF_SWAPFILE=/mnt/sata-sda/var/swap
$ CONF_SWAPSIZE=2048
$ CONF_MAXSWAP=4096
sudo dphys-swapfile setup
sudo dphys-swapfile swapon
sudo reboot
13. Install docker
https://phoenixnap.com/kb/docker-on-raspberry-pi
sudo apt-get update && sudo apt-get upgrade
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh
sudo usermod -aG docker $USER
14. Move docker to the new partition
sudo nano /etc/docker/daemon.json
{
	"data-root": "/mnt/sata-sda/var/lib/docker"
}
15. External antenna: 
dtparam=ant2

## ATX WATCHDOG

