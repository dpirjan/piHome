SUMMARY = "Qt5 demo packages for Embedded Linux (Qt without X11)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

PR = "r0"

inherit packagegroup

RDEPENDS_${PN} = " \
	qtsmarthome \
	qt5everywheredemo \
	cinematicexperience \
	quitindicators \
	quitbattery \
	quitindicators \
	qtsmarthome \
	cinematicexperience \
	qt5ledscreen \
	qt5nmapcarousedemo \
	qt5nmapper \
"
