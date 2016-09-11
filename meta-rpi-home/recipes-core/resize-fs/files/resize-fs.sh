#!/bin/bash

function testExitCode {
    "$@"
    local status=$?
    if [ $status -ne 0 ]; then
        echo "error with $1" >&2
    fi
    return $status
}

/bin/echo "Un-mounting the /dev/mmcblk0p2 partition"
testExitCode /bin/umount /dev/mmcblk0p2

/bin/echo "Resizing the partition with fdisk"
testExitCode /bin/echo -e "d\n2\nn\np\n3\n\n\nn\np\n2\n\n\nd\n3\nw" | /sbin/fdisk /dev/mmcblk0

fdisk -l /dev/mmcblk0

/bin/echo "Resizing the partition with resize2fs"
testExitCode /sbin/resize2fs /dev/mmcblk0p2

exit 0