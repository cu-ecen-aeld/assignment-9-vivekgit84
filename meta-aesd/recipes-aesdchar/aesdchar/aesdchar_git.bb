# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-vivekgit84.git;protocol=ssh;branch=master"
SRC_URI += "file://aesd-init.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "1207296653785b5d887a55913b2956dcbe178894"


S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesd-init.sh"

FILES:${PN} += "${sysconfdir}/*"

do_configure () {
        :
}

do_compile () {
        oe_runmake
}

#KERNEL_VERSION = "5.15.124-yocto-standard"

do_install () {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/aesd-init.sh ${D}${sysconfdir}/init.d/

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    
    install -d ${D}/etc/aesdchar_startup
    install -m 0755 ${S}/aesdchar_unload ${D}/etc/aesdchar_startup/aesdchar_unload
    install -m 0755 ${S}/aesdchar_load ${D}/etc/aesdchar_startup/aesdchar_load
    
    module_do_install
}

