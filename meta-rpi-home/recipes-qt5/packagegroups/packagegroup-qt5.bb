SUMMARY = "Qt5 for Embedded Linux (Qt without X11)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

PR = "r0"

inherit packagegroup

RDEPENDS_${PN} = " \
	qtbase \
	qtbase-tools \
	qtbase-plugins \
	qtdeclarative \
	qtdeclarative-plugins \
	qtdeclarative-qmlplugins \
	qtdeclarative-tools \
	qtgraphicaleffects-qmlplugins \
	qtwebsockets \
	qtwebsockets-qmlplugins \
	qtquickcontrols \
	qtquickcontrols2 \
	qtquick1 \
	qtvirtualkeyboard \
"
