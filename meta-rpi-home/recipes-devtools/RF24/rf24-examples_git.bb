DESCRIPTION = "Recipe for compiling the examples provided in the rf24 library package."
HOMEPAGE = "http://tmrh20.github.io/"
SECTION = "devel/libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://RF24.h;endline=8;md5=f70af29e19cece3ed8668649bdf839c4"

SRCBRANCH = "master"
SRCREV = "8ea5127078394591a0af5b409c38fa11cb0677d9"

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.com/TMRh20/RF24.git;protocol=ssh;branch=${SRCBRANCH}"

DEPENDS += " \
	rf24 \
"

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

EXTRA_OECONF_append_raspberrypi += "--driver=RPi --soc=BCM2835"
EXTRA_OECONF_append_raspberrypi2 += "--driver=RPi --soc=BCM2835"
EXTRA_OECONF_append_raspberrypi3 += "--driver=RPi --soc=BCM2836"

do_compile() {
	cd ${S}/examples_linux
	oe_runmake all
	cd ${S}/examples_linux/interrupts
	oe_runmake all
}

do_install() {
	cd ${S}/examples_linux
	oe_runmake install
	cd ${S}/examples_linux/interrupts
	oe_runmake install
}

FILES_${PN}-dbg += "${datadir}/${PN}/.debug"
FILES_${PN} += "${datadir}"
