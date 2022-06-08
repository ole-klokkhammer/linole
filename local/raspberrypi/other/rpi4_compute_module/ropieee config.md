/boot/config.txt
# See /boot/overlays/README for all available options
gpu_mem=64
initramfs initramfs-linux.img followkernel
boot_delay=1
max_usb_current=1
dtparam=spi=off
start_x=0
dtparam=eee=off
dtparam=eth_max_speed=100
dtparam=eth_led0=4
dtparam=audio=off
arm_freq=2200
over_voltage=8
gpu_freq=500
dtparam=ant2
dtparam=krnbt=on
enable_uart=0
# AUDIO HAT
dtoverlay=iqaudio-dacplus,unmute_amp