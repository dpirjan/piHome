# Base this image on core-image-minimal
include recipes-core/images/core-image-minimal.bb

# Include modules in rootfs
IMAGE_INSTALL += " \
	kernel-modules \
	\
	qtbase \
	qtbase-fonts \
	qtbase-plugins \
	qtconnectivity \
	qtconnectivity-qmlplugins \
	qtdeclarative \
	qtdeclarative-plugins \
	qtdeclarative-qmlplugins \
	qtdeclarative-tools \
	qtgraphicaleffects-qmlplugins \
	qtimageformats-plugins \
	qtmultimedia \
	qtmultimedia-plugins \
	qtmultimedia-qmlplugins \
	qtquickcontrols-qmlplugins \
	qtscript \
	qtsensors \
	qtsensors-plugins \
	qtsensors-qmlplugins \
	qtsvg \
	qtsvg-plugins \
	qtsystems \
	qtsystems-qmlplugins \
	qtwebchannel \
	qtwebchannel-qmlplugins \
	qtwebsockets \
	qtwebsockets-qmlplugins \
	qtxmlpatterns \
	\
	wiringpi \
	\
"

SPLASH = "psplash-raspberrypi"

IMAGE_FEATURES += "ssh-server-dropbear splash"