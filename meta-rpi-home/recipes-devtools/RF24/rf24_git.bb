DESCRIPTION = "A library to "
HOMEPAGE = "http://tmrh20.github.io/"
SECTION = "devel/libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://RF24.h;endline=8;md5=f70af29e19cece3ed8668649bdf839c4"

SRCBRANCH = "master"
SRCREV = "57a74e8c7b928bbccf4f7691e4e2593012dda40e"

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.com/TMRh20/RF24.git;protocol=ssh;branch=${SRCBRANCH}"

