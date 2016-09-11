FILESEXTRAPATHS_prepend := "${THISDIR}/systemd:"

SRC_URI += "\
	file://wired.network \
"

PACKAGECONFIG_append = " networkd resolved"

do_install_append() {
	install -d ${D}/etc/systemd/network/
	install -m 644 ${WORKDIR}/wired.network ${D}/etc/systemd/network

	# enable timesyncd service
	install -d ${D}${sysconfdir}/systemd/system/sysinit.target.wants
	ln -sf ${systemd_unitdir}/system/systemd-timesyncd.service \
	    ${D}${sysconfdir}/systemd/system/sysinit.target.wants/systemd-timesyncd.service
	# enable timedated service
	ln -sf ${systemd_unitdir}/system/systemd-timedated.service \
	    ${D}${sysconfdir}/systemd/system/sysinit.target.wants/systemd-timedated.service
}

FILES_${PN} += " \
	{sysconfdir}/systemd/network/* \
"