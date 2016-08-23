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
	install -d ${D}${libdir}/
	install -m 0755 ${B}/dataManager/databaseManagerService/databaseManager ${D}${datadir}/${PN}
	install -m 0755 ${B}/dataManager/databaseManagerInfo/*.so* ${D}${libdir}/${PN}
	install -m 0755 ${B}/sensorManager/sensorManager ${D}${datadir}/${PN}
	install -m 0755 ${B}/dataManager/mailManagerService/mailManager ${D}${datadir}/${PN}
}

FILES_${PN}-dbg += "\
	${datadir}/${PN}/.debug \
	${libdir}/${PN}/.debug \
"

FILES_${PN} += "\
	${datadir} \
	${libdir} \
	${ROOT_HOME}/.piHome \
"
