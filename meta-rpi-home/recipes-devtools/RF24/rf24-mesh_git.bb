DESCRIPTION = "A simple and seamless 'mesh' layer for sensor networks. Designed to interface directly with with the RF24Network Development library, an OSI Network Layer using nRF24L01(+) radios driven by the newly optimized RF24 library fork."
HOMEPAGE = "http://tmrh20.github.io/RF24Mesh/"
SECTION = "devel/libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://RF24Mesh.h;endline=2;md5=e1c06d85ae7b8b032bef47e42e4c08f9"

SRCBRANCH = "master"
SRCREV = "ce3531454b5ca14c83684029e1405dc833bffe93"

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.com/TMRh20/RF24Mesh.git;protocol=ssh;branch=${SRCBRANCH} \
	file://0001-RF24Mesh-Fix-for-cross-compilation.patch \
"

inherit autotools-brokensep

DEPENDS += " \
	rf24 \
	rf24-network \
"
EXTRA_OEMAKE = " \
	PREFIX=${D}/usr \
	EXAMPLE_DIR=${D}${datadir}/${PN} \
	SYSROOT_DIR=${STAGING_DIR_HOST} \
"

do_compile_append() {
	mkdir -p ${D}${includedir}/RF24Mesh
	mkdir -p ${D}${libdir}
	mkdir -p ${D}${datadir}/${PN}
	oe_runmake install
	cd ${S}/examples_RPi
	oe_runmake install
}

do_install() {
}

FILES_${PN}-dbg += "${datadir}/${PN}/.debug"
FILES_${PN} += "${datadir}"
