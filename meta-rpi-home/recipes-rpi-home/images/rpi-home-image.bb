rpi-hwup-image.bb   [----]  0 L:[  1+ 0   1/  8] *(0   / 161b) 0035 0x023                                      [*][X]
# Base this image on core-image-minimal
include recipes-core/images/core-image-minimal.bb

# Include modules in rootfs
IMAGE_INSTALL += " \
	kernel-modules \
"

SPLASH = "psplash-raspberrypi"

IMAGE_FEATURES += "ssh-server-dropbear splash"