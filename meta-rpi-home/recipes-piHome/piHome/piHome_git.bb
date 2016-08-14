SUMMARY = "Qt5 smarthome and smartalarm application"
DESCRIPTION = "This is a smarthome and smartalarm application for raspberrypi based on Qt5"
LICENSE = "MIT & Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d86c86e0cb119952952dd5d2684eb8c2"
LIC_FILES_CHKSUM = "file://settingsManager/simplecrypt.h;endline=26;md5=841a77cd57734a3e1a31443ad7917566"

SRCBRANCH = "master"
SRCREV = "${AUTOREV}"

SRC_URI = "git://git@github.com/aurelian17/piQtSmartHome.git;protocol=ssh;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit qmake5

DEPENDS = "\
	qtbase \
	wiringpi \
	rf24-network \
	rf24 \
"

do_install() {
	install -d ${D}/${ROOT_HOME}/.piHome
	install -d ${D}${datadir}/${PN}
	install -d ${D}${libdir}/${PN}
	install -m 0755 ${B}/databaseManager/databaseManagerService/databaseManager ${D}${datadir}/${PN}
	install -m 0755 ${B}/databaseManager/databaseManagerInfo/*.so* ${D}${libdir}/${PN}
	install -m 0755 ${B}/sensorManager/sensorManager ${D}${datadir}/${PN}
	install -m 0755 ${B}/settingsManager/settingsManager ${D}${datadir}/${PN}
}

FILES_${PN}-dbg += " \
	${datadir}/${PN}/.debug \
	${libdir}/${PN}/.debug \
"

FILES_${PN} += " \
	${datadir} \
	${libdir} \
	${ROOT_HOME}/.piHome \
"
