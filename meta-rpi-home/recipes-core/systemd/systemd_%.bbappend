FILESEXTRAPATHS_prepend := "${THISDIR}/systemd:"

SRC_URI += "\
	file://wired.network \
	file://timesyncd.conf \
"

PACKAGECONFIG_append = " networkd resolved"

do_install_append() {
	install -d ${D}/etc/systemd/network/
	install -m 0644 ${WORKDIR}/wired.network ${D}${sysconfdir}/systemd/network
	install -m 0644 ${WORKDIR}/timesyncd.conf ${D}${sysconfdir}/systemd

	# enable timesyncd service
	install -d ${D}${sysconfdir}/systemd/system/sysinit.target.wants
	ln -sf ${systemd_unitdir}/system/systemd-timesyncd.service \
	    ${D}${sysconfdir}/systemd/system/sysinit.target.wants/systemd-timesyncd.service
}

FILES_${PN} += " \
	${sysconfdir}/systemd \
"