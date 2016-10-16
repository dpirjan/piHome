#!/bin/sh
/sbin/resize2fs /dev/mmcblk0p2
/bin/mount /dev/mmcblk0p1 /mnt
/bin/sed -i 's/\<rw\>//g' /mnt/cmdline.txt
/bin/cat /mnt/cmdline.txt
/bin/umount /dev/mmcblk0p1
exit 0
