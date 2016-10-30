SUMMARY = "Qt5 smartHome and smartAlarm application"
DESCRIPTION = "This is a smartHome and smartAlarmSystem application for raspberrypi based on Qt5"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "\
		file://COPYRIGHT;md5=000b5abf9a83d7e3bfca04095495c744 \
		file://dataManager/mailManagerService/simplecrypt.h;endline=26;md5=841a77cd57734a3e1a31443ad7917566 \
"

SRCBRANCH = "master"
SRCREV = "${AUTOREV}"

SRC_URI = "git://git@github.com/aurelian17/piQtSmartHome.git;protocol=ssh;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit qmake5 systemd

DEPENDS = "\
	systemd \
	dbus \
	qtbase \
	qtquick1 \
	qtquickcontrols \
	qtquickcontrols2 \
	wiringpi \
	rf24 \
	rf24-network \
	rf24-mesh \
"

SYSTEMD_SERVICE_${PN} = "\
	piHomeDatabase.service \
	piHomeMail.service \
	piHomeSensor.service \
"

do_install_append() {
	install -d ${D}/${ROOT_HOME}/.piHome
	install -d ${D}/${ROOT_HOME}/.piHome/logging
	install -d ${D}/${ROOT_HOME}/.config
	install -d ${D}/${ROOT_HOME}/.config/piHome
	install -d ${D}/${systemd_unitdir}/system
	install -d ${D}/${sysconfdir}/dbus-1/system.d
}

FILES_${PN}-dbg += "\
	${datadir}/${PN}/.debug \
	${libdir}/.debug \
"

FILES_${PN} += "\
	${ROOT_HOME}/.piHome \
	${ROOT_HOME}/.config \
	${systemd_unitdir}/system \
	${sysconfdir}/dbus-1 \
"
