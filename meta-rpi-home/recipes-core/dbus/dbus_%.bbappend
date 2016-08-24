do_install_append() {
	install -d ${D}${sysconfdir}/dbus-1/session.d
	install ${B}/bus/session.conf ${D}${sysconfdir}/dbus-1/session.d/session-local.conf
}