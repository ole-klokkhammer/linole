# Setting up
* rpi-boot to mount disk
  git clone --depth=1 https://github.com/raspberrypi/usbboot
  https://www.jeffgeerling.com/blog/2020/how-flash-raspberry-pi-os-compute-module-4-emmc-usbboot
* imager -> ubuntu-server 64bit
* touch ssh /boot
* edit /boot/config.txt -> insert config.txt
* initial boot
* change password
* change hostname: sudo hostnamectl set-hostname <nodex>
* sudo apt-get update
* sudo apt-get upgrade
* sudo apt update
* sudo apt upgrade
* sudo apt install linux-modules-extra-raspi

## setting up fans
sudo apt-get install -y i2c-tools
https://github.com/neg2led/cm4io-fan
https://www.jeffgeerling.com/blog/2021/controlling-pwm-fans-raspberry-pi-cm4-io-boards-emc2301


## Setup unattended updates
sudo apt install unattended-upgrades
sudo nano /etc/apt/apt.conf.d/50unattended-upgrade
Unattended-Upgrade::Allowed-Origins {
"${distro_id}:${distro_codename}";
"${distro_id}:${distro_codename}-security";
//      "${distro_id}:${distro_codename}-updates";
//      "${distro_id}:${distro_codename}-proposed";
//      "${distro_id}:${distro_codename}-backports";
};
Unattended-Upgrade::Package-Blacklist {
//      "vim";
//      "libc6";
//      "libc6-dev";
//      "libc6-i686";
};
sudo nano /etc/apt/apt.conf.d/20auto-upgrades
APT::Periodic::Update-Package-Lists "1";
APT::Periodic::Download-Upgradeable-Packages "1";
APT::Periodic::AutocleanInterval "7";
APT::Periodic::Unattended-Upgrade "1";

## Setup disk monitoring
Make sure the disks stay mounted