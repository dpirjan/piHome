# Base this image on core-image-minimal

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
	bcm2835-tests \
	\
	packagegroup-rf24\
	\
	gdb \
	\
	pismarthome \
	piunitmaster \
	pirf24 \
	\
	e2fsprogs \
	e2fsprogs-resize2fs \
	resize-fs \
	tzdata \
	tzdata-africa \
	tzdata-americas \
	tzdata-asia \
	tzdata-australia \
	tzdata-europe \
	\
	packagegroup-benchmarks \
	\
"

SPLASH = "psplash-raspberrypi"

IMAGE_FEATURES += "ssh-server-dropbear splash"
