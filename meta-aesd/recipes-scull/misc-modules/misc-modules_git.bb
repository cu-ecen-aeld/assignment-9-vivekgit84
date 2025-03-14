# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-vivekgit84.git;protocol=ssh;branch=main"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "3381a5bf19f3554e8368ff1449281f813b9ee579"

S = "${WORKDIR}/git"

FILES:${PN} += "${sysconfdir}/init.d/mod-init"

inherit module

EXTRA_OEMAKE += " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"

# Specify the package that provides the init script
INITSCRIPT_PACKAGES = "${PN}"
# Set the name of the init script
INITSCRIPT_NAME = "mod-init"

# Optionally define default runlevels for the script
INITSCRIPT_PARAMS = "defaults"

# Unable to find means of passing kernel path into install makefile - if kernel path is hardcoded you will need to patch the makefile. Note that the variable KERNEL_SRC will be passed in as the kernel source path.

do_install () {
				# Create the directory for the kernel module if it doesn't exist
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra

    # Install the misc-modules.ko kernel module into the proper location
    install -m 0644 ${S}/misc-modules/misc-modules.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
		
		# Create the init.d directory if it doesn't exist
    		install -d ${D}${sysconfdir}/init.d 	
    	 	# Install the misc modules initialization script into init.d
         	install -m 0755 ${S}/mod-init ${D}${sysconfdir}/init.d/mod-init
    		# Register the script with update-rc.d to handle runlevels
    		update-rc.d -r ${D} mod-init defaults
    
    }
