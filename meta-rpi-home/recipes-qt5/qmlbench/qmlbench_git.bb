DESCRIPTION = "qmlbench provides a set of simple benchmarks which aim \
to find how many times a certain operation can be performed per frame \
while still sustaining a perfect velvet framerate. The benchmarks \
exercise a very large part of the Qt Quick / QML / Qt Gui / Qt Core \
stack and can in many ways be considered a decent metric for overall \
Qt performance."

HOMEPAGE = "https://github.com/sletta/qmlbench"
LICENSE  = "CLOSED"
PR       = "r1"
S        = "${WORKDIR}/git"

DEPENDS        += "qtbase qtdeclarative"
RDEPENDS_${PN} += "qtbase qtdeclarative"

SRC_URI = " \
	git://github.com/sletta/qmlbench.git;protocol=https;branch=master \
	file://startQmlBench.sh \
"
SRCREV = "5dcc804ead9717dedc4a18d82ea4b25dfa557170"

inherit qmake5

do_install(){
   install -d ${D}${datadir}/${PN}
   cp -r ${S}/benchmark ${D}${datadir}/${PN}
   install -m 0655 ${B}/qmlbench ${D}${datadir}/${PN}
   install -m 0755 ${WORKDIR}/startQmlBench.sh ${D}${datadir}/${PN}
}

FILES_${PN}-dbg += "${datadir}/${PN}/.debug"