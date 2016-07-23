SUMMARY = "Library packages for interfacing with NRF24L01+ modules over SPi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

PR = "r0"

inherit packagegroup

RDEPENDS_${PN} = " \
	rf24 \
	rf24-network \
	rf24-mesh \
"


