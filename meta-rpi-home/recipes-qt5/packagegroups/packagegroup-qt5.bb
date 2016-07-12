SUMMARY = "Qt5 for Embedded Linux (Qt without X11)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

PR = "r0"

inherit packagegroup

RDEPENDS_${PN} = " \
	qtsvg \
	qtsvg-plugins \
	qtbase \
	qtbase-fonts \
	qtbase-tools \
	qtbase-plugins \
	qtdeclarative \
	qtdeclarative-plugins \
	qtdeclarative-qmlplugins \
	qtdeclarative-tools \
	qtgraphicaleffects-qmlplugins \
	qtscript \
	qtsensors \
	qtsensors-plugins \
	qtsensors-qmlplugins \
	qtwebchannel \
	qtwebchannel-qmlplugins \
	qtwebsockets \
	qtwebsockets-qmlplugins \
	qt3d \
	qtimageformats \
	qtimageformats-plugins \
"
