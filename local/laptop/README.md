# Pc speaker beep
* lsmod | grep pcspkr # check if its loaded
* rmmod pcspkr # temporarily remove
* echo "blacklist pcspkr" > /etc/modprobe.d/nobeep.conf ## add to blacklist to prevent loading

# Manjaro power management
* https://wiki.manjaro.org/index.php?title=Power_Management
* pamac install laptop-mode-tools
* sudo systemctl enable --now laptop-mode.service
* open lmt-config-ui and disable sleep for wifi etc
* systemd-swap: https://wiki.manjaro.org/index.php?title=Swap