DESCRIPTION = "Optimized library for implementation of OSI Network Layer with nRF24L01(+) radios driven by the newly optimized RF24 library fork."
HOMEPAGE = "http://tmrh20.github.io/RF24Network/"
SECTION = "devel/libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://RF24Network.h;endline=8;md5=9a66103fdbc8bd54d5fe8654b61cf6ea"

SRCBRANCH = "master"
SRCREV = "3fe2560f8d22cfbdeed52e327eb76524aff528b4"

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.com/TMRh20/RF24Network.git;protocol=ssh;branch=${SRCBRANCH} \
	file://0001-RF24Network-examples-Fix-cross-compilation.patch \
"

DEPENDS += " \
	rf24 \
	rf24-network \
"

inherit autotools-brokensep

DEPENDS += " \
	rf24 \
"

INSANE_SKIP_rf24-network += "dev-deps"

EXTRA_OEMAKE = " \
	PREFIX=${D}/usr \
	EXAMPLE_DIR=${D}${datadir}/${PN} \
"

do_compile() {
	cd ${S}/examples_RPi
	oe_runmake all
}

do_install() {
	install -d ${D}${datadir}/${PN}
	install -m 755 ${S}/examples_RPi/helloworld_rx ${D}${datadir}/${PN}
	install -m 755 ${S}/examples_RPi/helloworld_tx ${D}${datadir}/${PN}
	install -m 755 ${S}/examples_RPi/rx-test ${D}${datadir}/${PN}
	install -m 644 ${S}/examples_RPi/temperature.txt ${D}${datadir}/${PN}
}

FILES_${PN}-dbg += "${datadir}/${PN}/.debug"
FILES_${PN} += "${datadir}"
