DESCRIPTION = "piHome RF24Mesh master node example based on RaspberryPi"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS_prepend := "${THISDIR}/files/:"

SRC_URI += " \
	file://UnitMasterRPI.c \
	file://UnitMasterRPICPP.cpp \
"

S = "${WORKDIR}"

DEPENDS += " \
	rf24 \
	rf24-network \
	rf24-mesh \
"

do_compile() {
	$CXX $CXXFLAGS -g -std=gnu++11 -Wall -W -D_REENTRANT -fPIC ${S}/UnitMasterRPI.c -lwiringPi -lrf24mesh -lrf24network -lrf24 -lpthread -o UnitMasterRPI
	$CXX $CXXFLAGS -g -std=gnu++11 -Wall -W -D_REENTRANT -fPIC ${S}/UnitMasterRPICPP.cpp -lwiringPi -lrf24mesh -lrf24network -lrf24 -lpthread -o UnitMasterRPICPP
}

do_install() {
	install -d ${D}${datadir}/${PN}
	install -c -m 0755 ${B}/UnitMasterRPI ${D}${datadir}/${PN}
	install -c -m 0755 ${B}/UnitMasterRPICPP ${D}${datadir}/${PN}
}

FILES_${PN} += " \
	${datadir}/${PN}/UnitMasterRPI \
	${datadir}/${PN}/UnitMasterRPICPP \
"
