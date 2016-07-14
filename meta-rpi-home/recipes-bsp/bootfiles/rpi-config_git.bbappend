do_deploy_append() {
	if [ -n "${ENABLE_WAVESHARE_7INCH_CAPACITIVE_HDMI_DISPLAY}" ]; then
		echo "" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		echo "##Support for Waveshare 7inch HDMI Capacitive Display" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		echo "# set current over USB to 1.2A" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		echo "max_usb_current=1" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		echo "" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		echo "" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		sed -i '/#overscan_left=0/ c\overscan_left=0' ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		sed -i '/#overscan_right=0/ c\overscan_right=0' ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		sed -i '/#overscan_top=0/ c\overscan_top=0' ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		sed -i '/#overscan_bottom=0/ c\overscan_bottom=0' ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		sed -i '/#hdmi_drive=2/ c\hdmi_drive=1' ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		sed -i '/#hdmi_ignore_edid=0xa5000080/ c\hdmi_ignore_edid=0xa5000080' ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		sed -i '/#hdmi_group=1/ c\hdmi_group=2' ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		sed -i '/#hdmi_mode=1/ c\hdmi_mode=87' ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		sed -i '/#config_hdmi_boost=0/ c\config_hdmi_boost=4' ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		sed -i '/#framebuffer_height=0/ c\framebuffer_height=600' ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		sed -i '/#framebuffer_width=0/ c\framebuffer_width=1024' ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		echo "# 1024x600@60hz display" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
		echo "hdmi_cvt=1024 600 60 6 0 0 0" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
	fi
}