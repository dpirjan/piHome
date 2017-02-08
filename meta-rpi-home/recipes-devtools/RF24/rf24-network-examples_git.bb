DESCRIPTION = "Recipe for compiling the examples from the RF24-network library package."
HOMEPAGE = "http://tmrh20.github.io/RF24Network/"
SECTION = "devel/libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://RF24Network.h;endline=8;md5=9a66103fdbc8bd54d5fe8654b61cf6ea"

SRCBRANCH = "master"
SRCREV = "c30db56cf8444db1cbf15d470ce92eb59597c3bc"

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.com/TMRh20/RF24Network.git;protocol=ssh;branch=${SRCBRANCH} \
	file://0001-RF24Network-examples-Fix-cross-compilation.patch \
"

DEPENDS += " \
	rf24 \
	rf24-network \
"

inherit autotools-brokensep

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
