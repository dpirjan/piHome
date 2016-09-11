EXTRA_OECONF_append += " \
	-opengl es2 \
	-eglfs \
	-qpa eglfs-brcm \
"

PACKAGECONFIG_GL = "gles2"
LDFLAGS_prepend = " -lGLESv2 "

RDEPENDS_${PN} += " \
	libinput \
	userland \
"

PACKAGECONFIG_append = " sql-sqlite"

COMPATIBLE_MACHINE = "(raspberrypi3|raspberrypi2|raspberrypi)"

QT_CONFIG_FLAGS_append = " \
	-device-option CROSS_COMPILE={TARGET_PREFIX} \
	-I${STAGING_DIR_TARGET}/${includedir}/interface/vcos/pthreads \
	-I${STAGING_DIR_TARGET}/${includedir}/interface/vmcs_host/linux \
"
