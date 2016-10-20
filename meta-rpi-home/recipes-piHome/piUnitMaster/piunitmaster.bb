DESCRIPTION = "piHome RF24Mesh master node example based on RaspberryPi"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS_prepend := "${THISDIR}/files/:"

SRC_URI += "file://UnitMasterRPI.c"

S = "${WORKDIR}"

DEPENDS += " \
	rf24 \
	rf24-network \
	rf24-mesh \
"

do_compile() {
    $CXX $CXXFLAGS ${S}/UnitMasterRPI.c -lrf24mesh -lrf24network -lrf24 -o UnitMasterRPI
}

do_install() {
	install -d ${D}${datadir}/${PN}
	install -c -m 0755 ${B}/UnitMasterRPI ${D}${datadir}/${PN}
}

FILES_${PN} += "${datadir}/${PN}/UnitMasterRPI"