# Base this image on core-image-minimal
include recipes-core/images/core-image-minimal.bb

# Include modules in rootfs
IMAGE_INSTALL += " \
	kernel-modules \
	\
	packagegroup-core-boot \
	packagegroup-core-full-cmdline \
	userland \
	\
	cairo \
	pango \
	fontconfig \
	freetype \
	libdrm \
	libinput \
	\
	qtbase \
	qtbase-fonts \
	qtbase-plugins \
	qtbase-tools \
	qtdeclarative \
	qtdeclarative-plugins \
	qtdeclarative-qmlplugins \
	qtdeclarative-tools \
	qtgraphicaleffects-qmlplugins \
	qtimageformats-plugins \
	qtscript \
	qtsensors \
	qtsensors-plugins \
	qtsensors-qmlplugins \
	qtsvg \
	qtsvg-plugins \
	qtwebchannel \
	qtwebchannel-qmlplugins \
	qtwebsockets \
	qtwebsockets-qmlplugins \
	\
	qtsmarthome \
	qt5everywheredemo \
	cinematicexperience \
	\
	wiringpi \
	\
"

SPLASH = "psplash-raspberrypi"

IMAGE_FEATURES += "ssh-server-dropbear splash"