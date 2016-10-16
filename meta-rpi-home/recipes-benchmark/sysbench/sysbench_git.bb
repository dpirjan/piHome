DESCRIPTION = "SysBench is a modular, cross-platform and multi-threaded \
benchmark tool for evaluating OS parameters that are important for a \
system running a database under intensive load. \
The idea of this benchmark suite is to quickly get an impression about \
system performance without setting up complex database benchmarks or even \
without installing a database at all."

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
SRCREV = "b953dab30fe520c9513ee950f352b32a6a86eb1f"
S = "${WORKDIR}/git"
SRC_URI[md5sum] = "7b0a8dddbf16e2dc67a9694539c512d6"
SRC_URI[sha256sum] = "3c27990248320dcaa5c7f128c94105414abc0e8b65f60c50c685b7e47055dfd5"

SRC_URI = " \
	git://github.com/akopytov/sysbench.git;protocol=https;branch=1.0 \
"

inherit autotools-brokensep

EXTRA_OECONF += " --without-mysql "