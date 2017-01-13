DESCRIPTION = "Optimized library for nRF24L01(+) that is simple to use for beginners, but yet offers a lot for advanced users. It also has a lot of good examples how to use the library."
HOMEPAGE = "http://tmrh20.github.io/"
SECTION = "devel/libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://RF24.h;endline=8;md5=f70af29e19cece3ed8668649bdf839c4"

SRCBRANCH = "master"
#SRCREV = "e234ba7142d6fcd30b37cf45991099336473567a"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

#SRC_URI = "git://git@github.com/TMRh20/RF24.git;protocol=ssh;branch=${SRCBRANCH}"
SRC_URI = "git://git@github.com/aurelian17/RF24.git;protocol=ssh;branch=${SRCBRANCH}"

LIBNAME = "librf24.so.1.2.0"
LIBDEPRECATE = "librf24-bcm.so"
ARCH_DIR = "utility"
DRIVER_DIR = "utility/wiringPi"

DEPENDS += " \
	wiringpi \
"

inherit autotools-brokensep

EXTRA_OECONF = " \
	--target=${TARGET_SYS} \
	--host=${BUILD_SYS} \
	--build=${BUILD_SYS} \
	--prefix=${prefix} \
	--lib-dir=${D}${libdir} \
	--header-dir=${D}${includedir}/RF24 \
	--ldconfig='' \
	--with-libtool-sysroot=${STAGING_DIR_HOST} \
	--no-clean \
"

EXTRA_OECONF_append_raspberrypi += "--driver=wiringPi"
EXTRA_OECONF_append_raspberrypi2 += "--driver=wiringPi"
EXTRA_OECONF_append_raspberrypi3 += "--driver=wiringPi"

do_configure_append() {
	cp ${S}/${DRIVER_DIR}/includes.h ${S}/utility
}

do_install() {
	install -d ${D}${libdir}
	install -d ${D}${includedir}/RF24/${DRIVER_DIR}
	install -m 0755 ${LIBNAME} ${D}${libdir}
	ln -sf ${LIBNAME} ${D}${libdir}/librf24.so.1.2
	ln -sf ${LIBNAME} ${D}${libdir}/librf24.so.1
	ln -sf ${LIBNAME} ${D}${libdir}/librf24.so
	ln -sf ${LIBNAME} ${D}${libdir}/${LIBDEPRECATE}
	install -m 644 *.h ${D}${includedir}/RF24
	install -m 644 ${DRIVER_DIR}/*.h ${D}${includedir}/RF24/${DRIVER_DIR}
	install -m 644 ${ARCH_DIR}/*.h ${D}${includedir}/RF24/${ARCH_DIR}
}
