# Base this image on core-image-minimal
include recipes-core/images/core-image-minimal.bb

# Include modules in rootfs
IMAGE_INSTALL += " \
	kernel-modules \
	\
	packagegroup-core-boot \
	packagegroup-core-full-cmdline \
	zlib \
	userland \
	\
	cairo \
	pango \
	fontconfig \
	freetype \
	libdrm \
	libinput \
	\
	gstreamer \
	gst-meta-video \
	gst-plugins-base \
	gst-plugins-base-app \
	gst-plugins-good \
	gst-plugins-good-video4linux2 \
	\
	qtbase \
	qtbase-fonts \
	qtbase-plugins \
	qtbase-tools \
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
	qtsystems-tools \
	qtsystems-qmlplugins \
	qtwebchannel \
	qtwebchannel-qmlplugins \
	qtwebsockets \
	qtwebsockets-qmlplugins \
	qtxmlpatterns \
	qtwebkit \
	qtwebkit-qmlplugins \
	qtsmarthome \
	qt5everywheredemo \
	cinematicexperience \
	\
	wiringpi \
	\
"

SPLASH = "psplash-raspberrypi"

IMAGE_FEATURES += "ssh-server-dropbear splash"