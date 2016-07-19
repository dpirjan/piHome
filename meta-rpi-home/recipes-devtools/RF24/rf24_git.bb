DESCRIPTION = "Optimized library for nRF24L01(+) that is simple to use for beginners, but yet offers a lot for advanced users. It also has a lot of good examples how to use the library."
HOMEPAGE = "http://tmrh20.github.io/"
SECTION = "devel/libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://RF24.h;endline=8;md5=f70af29e19cece3ed8668649bdf839c4"

SRCBRANCH = "master"
SRCREV = "57a74e8c7b928bbccf4f7691e4e2593012dda40e"

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.com/TMRh20/RF24.git;protocol=ssh;branch=${SRCBRANCH}"

inherit autotools-brokensep

EXTRA_OECONF = " \
	--target=${TARGET_SYS} \
	--host=${BUILD_SYS} \
	--build=${BUILD_SYS} \
	--prefix=${prefix} \
	--lib-dir=${D}${libdir} \
	--header-dir=${D}${includedir}/RF24 \
	--examples-dir=${D}${datadir}/${PN} \
	--ldconfig='' \
	--with-libtool-sysroot=${STAGING_DIR_HOST} \
	--no-clean \
"
EXTRA_OECONF_append_raspberrypi2 += "--driver=RPi --soc=BCM2835"
EXTRA_OECONF_append_raspberrypi3 += "--driver=RPi --soc=BCM2836"

do_configure_append() {
	cp ${S}/utility/RPi/includes.h ${S}/utility
}

do_compile_append() {
	install -d ${D}${libdir}
	install -d ${D}${includedir}
	install -d ${D}${datadir}/${P}
	oe_runmake install
	cd ${S}/examples_linux
	oe_runmake all
	cd ${S}/examples_linux/interrupts
	oe_runmake all
}

do_install() {
	oe_runmake install
	cd ${S}/examples_linux
	oe_runmake install
	cd ${S}/examples_linux/interrupts
	oe_runmake install
}

FILES_${PN}-dbg += "${datadir}/${PN}/.debug"
FILES_${PN} += "${datadir}"
