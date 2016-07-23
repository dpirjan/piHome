SUMMARY = "Application/Examples packages for demonstrating the interfacing with NRF24L01+ modules over SPi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

PR = "r0"

inherit packagegroup

RDEPENDS_${PN} = " \
	rf24-examples \
	rf24-network-examples \
	rf24-mesh-examples \
"