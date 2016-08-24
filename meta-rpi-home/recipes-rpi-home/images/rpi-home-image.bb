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
	sqlite3 \
	\
	dbus \
	\
	openssl \
	ca-certificates \
	\
	openssh-sftp-server \
	\
	packagegroup-qt5 \
	\
	packagegroup-qt5-demos \
	\
	wiringpi \
	\
	packagegroup-rf24\
	packagegroup-rf24-examples \
	\
	gdb \
	\
	pismarthome \
	\
"

SPLASH = "psplash-raspberrypi"

IMAGE_FEATURES += "ssh-server-dropbear splash"