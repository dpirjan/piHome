LICENSE = "CLOSED"
SRCBRANCH = "master"
SRCREV = "${AUTOREV}"

SRC_URI = "git://git@github.com/aurelian17/piQtRF24.git;protocol=ssh;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

FILESEXTRAPATHS_prepend := "${THISDIR}/files/:"

inherit qmake5

DEPENDS = "\
	qtbase \
	qtquick1 \
	qtquickcontrols \
	qtquickcontrols2 \
	wiringpi \
	rf24 \
	rf24-network \
	rf24-mesh \
"

FILES_${PN}-dbg += "\
	${datadir}/${PN}/.debug \
"

FILES_${PN} += "\
	${datadir} \
"