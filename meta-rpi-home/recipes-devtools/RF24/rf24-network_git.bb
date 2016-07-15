DESCRIPTION = "Optimized library for implementation of OSI Network Layer with nRF24L01(+) radios driven by the newly optimized RF24 library fork."
HOMEPAGE = "http://tmrh20.github.io/RF24Network/"
SECTION = "devel/libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://RF24Network.h;endline=8;md5=9a66103fdbc8bd54d5fe8654b61cf6ea"

SRCBRANCH = "master"
SRCREV = "3fe2560f8d22cfbdeed52e327eb76524aff528b4"

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.com/TMRh20/RF24Network.git;protocol=ssh;branch=${SRCBRANCH} \
	file://0001-Fix-for-cross-compilation.patch \
"

inherit autotools-brokensep

DEPENDS += " \
	rf24 \
"
EXTRA_OEMAKE = " \
	PREFIX=${D}/usr \
	EXAMPLE_DIR=${D}${datadir}/${PN} \
	SYSROOT_DIR=${STAGING_DIR_HOST} \
"

do_compile_append() {
	mkdir -p ${D}${includedir}/RF24Network
	mkdir -p ${D}${libdir}
	mkdir -p ${D}${datadir}/${PN}
	oe_runmake install
	cd ${S}/examples_RPi
	oe_runmake install
	install ${S}/examples_RPi/temperature.txt ${D}${datadir}/${PN}
}

do_install() {
}

FILES_${PN}-dbg += "${datadir}/${PN}/.debug"
FILES_${PN} += "${datadir}"
