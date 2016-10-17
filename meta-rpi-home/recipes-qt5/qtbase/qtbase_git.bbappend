FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

SRC_URI += " \
	file://oe-device-extra.pri \
	file://0001-Add-win32-g-oe-mkspec-that-uses-the-OE_-environment.patch \
"

PACKAGECONFIG_GL = "gles2 eglfs"

PACKAGECONFIG_append = " \
	accessibility \
	alsa \
	cups \
	fontconfig \
	freetype \
	glib \
	iconv \
	icu \
	linuxfb \
	sql-sqlite \
	tslib \
	libinput \
	xkbcommon-evdev \
"

RDEPENDS_${PN} += " \
	libinput \
	userland \
"

COMPATIBLE_MACHINE = "(raspberrypi3|raspberrypi2|raspberrypi)"

EXTRA_OECONF_append += " \
	-opengl es2 \
	-eglfs \
	-qpa eglfs-brcm \
"

do_configure_prepend() {
    install -m 0644 ${WORKDIR}/oe-device-extra.pri ${S}/mkspecs
}
