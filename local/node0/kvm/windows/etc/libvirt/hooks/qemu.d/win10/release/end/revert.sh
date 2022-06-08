#!/bin/bash
set -x

# Re-Bind GPU to Nvidia Driver
virsh nodedev-reattach pci_0000_08_00_0
virsh nodedev-reattach pci_0000_08_00_1

# Unload VFIO-PCI Kernel Driver
modprobe -r vfio-pci
modprobe -r vfio_iommu_type1
modprobe -r vfio

modprobe nvidia_drm
modprobe nvidia_modeset
modprobe nvidia_uvm
modprobe nvidia

# Rebind VT consoles
echo 1 > /sys/class/vtconsole/vtcon0/bind
echo 1 > /sys/class/vtconsole/vtcon1/bind
# Some machines might have more than 1 virtual console. Add a line for each corresponding VTConsole

nvidia-xconfig --query-gpu-info > /dev/null 2>&1
echo "efi-framebuffer.0" > /sys/bus/platform/drivers/efi-framebuffer/bind

# Restart Display Manager
systemctl start display-manager.service
