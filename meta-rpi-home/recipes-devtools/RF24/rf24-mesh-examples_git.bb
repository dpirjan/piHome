DESCRIPTION = "Recipe for compiling the examples from the RF24-mesh library package."
HOMEPAGE = "http://tmrh20.github.io/RF24Mesh/"
SECTION = "devel/libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://RF24Mesh.h;endline=2;md5=e1c06d85ae7b8b032bef47e42e4c08f9"

SRCBRANCH = "master"
SRCREV = "a832458dbd066c7b8073a46f124ddec0fc9a9491"

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.com/TMRh20/RF24Mesh.git;protocol=ssh;branch=${SRCBRANCH} \
	file://0001-RF24Mesh-examples-Fix-cross-compilation.patch \
"

inherit autotools-brokensep

DEPENDS += " \
	rf24 \
	rf24-network \
	rf24-mesh \
"

EXTRA_OEMAKE = " \
	PREFIX=${D}/usr \
	EXAMPLE_DIR=${D}${datadir}/${PN} \
"

do_compile() {
	cd ${S}/examples_RPi
	oe_runmake all
}

do_install() {
	install -d ${D}${datadir}/${PN}
	install -m 755 ${S}/examples_RPi/RF24Mesh_Example ${D}${datadir}/${PN}
	install -m 755 ${S}/examples_RPi/RF24Mesh_Example_Master ${D}${datadir}/${PN}
}

FILES_${PN}-dbg += "${datadir}/${PN}/.debug"
FILES_${PN} += "${datadir}"
