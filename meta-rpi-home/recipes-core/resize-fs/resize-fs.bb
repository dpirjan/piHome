DESCRIPTION = "Resize RaspberryPi rootfs partition to maximum size via systemd service"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

SRC_URI = " \
	file://resize-fs.service \
	file://resize-fs.sh \
"

inherit systemd

SYSTEMD_SERVICE_${PN} = "resize-fs.service"

RDEPENDS_${PN} = "systemd e2fsprogs-resize2fs"

do_install() {
    install -d ${D}${systemd_unitdir}/system
    install -d ${D}${systemd_unitdir}/scripts
    install -c -m 0644 ${WORKDIR}/resize-fs.service ${D}${systemd_unitdir}/system
    install -m 0755 ${WORKDIR}/resize-fs.sh ${D}${systemd_unitdir}/scripts
}

FILES_${PN} = " \
	${base_libdir}/systemd/system/resize-fs.service \
	${base_libdir}/systemd/scripts/resize-fs.sh \
"

# As this package is tied to systemd, only build it when we're also building systemd.
python () {
    if not bb.utils.contains ('DISTRO_FEATURES', 'systemd', True, False, d):
        raise bb.parse.SkipPackage("'systemd' not in DISTRO_FEATURES")
}