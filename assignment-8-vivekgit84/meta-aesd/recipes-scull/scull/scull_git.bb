# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-vivekgit84.git;protocol=ssh;branch=main"
SRC_URI += "file://scull-init.sh"
SRC_URI += "file://Makefile"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "85d44fad5a4f4db843e765b55ae1edab2926e396"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull-init.sh"

FILES:${PN} += "${sysconfdir}/*"

do_configure () {
        :
}

do_compile () {
        oe_runmake
}

KERNEL_VERSION = "5.15.178-yocto-standard"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/scull-init.sh ${D}${sysconfdir}/init.d/

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/scull/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    
    install -d ${D}/etc/scull_startup
    install -m 0755 ${S}/scull/scull_unload ${D}/etc/scull_startup/scull_unload
    install -m 0755 ${S}/scull/scull_load ${D}/etc/scull_startup/scull_load
    
    module_do_install
}

