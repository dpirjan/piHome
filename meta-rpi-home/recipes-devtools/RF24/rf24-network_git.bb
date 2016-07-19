DESCRIPTION = "Optimized library for implementation of OSI Network Layer with nRF24L01(+) radios driven by the newly optimized RF24 library fork."
HOMEPAGE = "http://tmrh20.github.io/RF24Network/"
SECTION = "devel/libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://RF24Network.h;endline=8;md5=9a66103fdbc8bd54d5fe8654b61cf6ea"

SRCBRANCH = "master"
SRCREV = "3fe2560f8d22cfbdeed52e327eb76524aff528b4"

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.com/TMRh20/RF24Network.git;protocol=ssh;branch=${SRCBRANCH} \
	file://0001-RF24Network-Fix-cross-compilation.patch \
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
	install -d ${D}${includedir}/RF24Network
	install -d ${D}${libdir}
	oe_runmake all
	install -m 0755 ${S}/librf24network.so.1.0 ${D}${libdir}
	ln -sf librf24network.so.1.0 ${D}${libdir}/librf24network.so
	ln -sf librf24network.so.1.0 ${D}${libdir}/librf24network.so.1
	install -m 0644 ${S}/*.h ${D}${includedir}/RF24Network
	cd ${S}/examples_RPi
	oe_runmake all
}

do_install() {
	install -d ${D}${includedir}/RF24Network
	install -d ${D}${libdir}
	install -d ${D}${datadir}/${PN}
	install -m 0755 ${S}/librf24network.so.1.0 ${D}${libdir}
	ln -sf librf24network.so.1.0 ${D}${libdir}/librf24network.so
	ln -sf librf24network.so.1.0 ${D}${libdir}/librf24network.so.1
	install -m 0644 ${S}/*.h ${D}${includedir}/RF24Network
	install -m 755 ${S}/examples_RPi/helloworld_rx ${D}${datadir}/${PN}
	install -m 755 ${S}/examples_RPi/helloworld_tx ${D}${datadir}/${PN}
	install -m 755 ${S}/examples_RPi/rx-test ${D}${datadir}/${PN}
	install -m 644 ${S}/examples_RPi/temperature.txt ${D}${datadir}/${PN}
}

FILES_${PN}-dbg += "${datadir}/${PN}/.debug"
FILES_${PN} += "${datadir}"
