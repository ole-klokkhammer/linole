#!/bin/bash

# Helpful to read output when debugging
set -x

# Stop display manager
systemctl stop display-manager.service

# Unbind VTconsoles
echo 0 > /sys/class/vtconsole/vtcon0/bind
echo 0 > /sys/class/vtconsole/vtcon1/bind

# Unbind EFI-Framebuffer
echo efi-framebuffer.0 > /sys/bus/platform/drivers/efi-framebuffer/unbind

# Avoid a Race condition by waiting 2 seconds. This can be calibrated to be shorter or longer if required for your system
sleep 2

# Unload all Nvidia drivers
modprobe -r nvidia_drm
modprobe -r nvidia_modeset
modprobe -r nvidia_uvm
modprobe -r nvidia

# Load VFIO Kernel Module  
modprobe vfio-pci

# Unbind the GPU from display driver
virsh nodedev-detach pci_0000_08_00_0
virsh nodedev-detach pci_0000_08_00_1


