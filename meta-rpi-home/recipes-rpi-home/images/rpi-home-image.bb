# Base this image on core-image-minimal
# PACKAGES TO BE ADDED:
#raspi-config

include recipes-core/images/core-image-minimal.bb

# Include modules in rootfs
IMAGE_INSTALL += " \
	kernel-modules \
	\
	packagegroup-core-boot \
	packagegroup-core-full-cmdline \
	\
	userland \
	\
	cairo \
	pango \
	fontconfig \
	freetype \
	libdrm \
	libinput \
	\
	openssh-sftp-server \
	\
	dbus \
	\
	packagegroup-qt5 \
	\
	packagegroup-qt5-demos \
	\
	wiringpi \
	\
	rf24 \
	rf24-network \
	\
"

SPLASH = "psplash-raspberrypi"

IMAGE_FEATURES += "ssh-server-dropbear splash"