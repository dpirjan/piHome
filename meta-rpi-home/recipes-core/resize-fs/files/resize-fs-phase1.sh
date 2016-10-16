#!/bin/sh
/bin/echo -e "d\n2\nn\np\n3\n\n\nn\np\n2\n\n\nd\n3\nw" | /sbin/fdisk /dev/mmcblk0
/bin/sed -i 's/\<ExecStart=\/lib\/systemd\/scripts\/resize-fs-phase1.sh\>//g' /lib/systemd/system/resize-fs.service
/bin/sed -i '/#ExecStart=\/lib\/systemd\/scripts\/resize-fs-phase2.sh/ c\ExecStart=\/lib\/systemd\/scripts\/resize-fs-phase2.sh' /lib/systemd/system/resize-fs.service
/bin/sed -i '/#ExecStart=\/bin\/systemctl disable resize-fs.service/ c\ExecStart=\/bin\/systemctl disable resize-fs.service' /lib/systemd/system/resize-fs.service
/bin/cat /lib/systemd/system/resize-fs.service
/bin/systemctl daemon-reload
/sbin/reboot
exit 0
