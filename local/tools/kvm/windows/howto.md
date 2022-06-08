
The process of setting up a VM using virt-manager is mostly self-explanatory, as most of the process comes with fairly comprehensive on-screen instructions.

However, you should pay special attention to the following steps:

# When the VM creation wizard asks you to name your VM (final step before clicking "Finish"), check the "Customize before install" checkbox.
1. In the "Overview" section, set your firmware to "UEFI". If the option is grayed out, make sure that:
1.a Your hypervisor is running as a system session and not a user session. This can be verified by clicking, then hovering over the session in virt-manager. If you are accidentally running it as a user session, you must open a new connection by clicking "File" > "Add Connection..", then select the option from the drop-down menu station "QEMU/KVM" and not "QEMU/KVM user session".
2. In the "CPUs" section, change your CPU model to "host-passthrough". If it is not in the list, you will have to either type it by hand or by using virt-xml vmname --edit --cpu host-passthrough. This will ensure that your CPU is detected properly, since it causes libvirt to expose your CPU capabilities exactly as they are instead of only those it recognizes (which is the preferred default behavior to make CPU behavior easier to reproduce). Without it, some applications may complain about your CPU being of an unknown model.
3. If you want to minimize IO overhead, it's easier to setup #Virtio disk before installing

# REMEMBER TO UPDATE DEVICES IN HOOKS!

Follow this guide: https://github.com/joeknock90/Single-GPU-Passthrough
And this: https://wiki.archlinux.org/index.php/PCI_passthrough_via_OVMF#Configuring_libvirt

virtio driver: http://www.zeta.systems/blog/2018/07/03/Installing-Virtio-Drivers-In-Windows-On-KVM/

https://heiko-sieger.info/creating-a-windows-10-vm-on-the-amd-ryzen-9-3900x-using-qemu-4-0-and-vga-passthrough/

Bonus:
https://heiko-sieger.info/running-windows-10-on-linux-using-kvm-with-vga-passthrough/
https://gist.github.com/vwxyzjn/ab94cb5d759360f252b0441bf8d998b4
https://leduccc.medium.com/improving-the-performance-of-a-windows-10-guest-on-qemu-a5b3f54d9cf5
