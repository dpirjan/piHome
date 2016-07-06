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

QT_CONFIG_FLAGS_append_raspberrypi2 = " \
	-device-option CROSS_COMPILE={TARGET_PREFIX} \
	-I${STAGING_DIR_TARGET}/${includedir}/interface/vcos/pthreads \
	-I${STAGING_DIR_TARGET}/${includedir}/interface/vmcs_host/linux \
"
