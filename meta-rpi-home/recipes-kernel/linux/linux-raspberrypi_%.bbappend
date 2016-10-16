do_deploy_append() {
	#Add rw to cmdline.txt in order to be able to resize the /dev/mmcblk0p2 at first boot
	if [ -n "${RESIZE_FS_FIRST_BOOTUP}" ]; then
		sed -i 's/\brootfstype=ext4\b/& rw/' ${DEPLOYDIR}/bcm2835-bootfiles/cmdline.txt
	fi
}
