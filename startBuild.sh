#!/bin/sh

clear

SCRIPT_NAME="startBuild.sh"

# Check that it was sourced
cmd=$(basename "$0")
[ "$cmd" = "$SCRIPT_NAME" ] && {
	echo "No, the script $SCRIPT_NAME needs to be _sourced_ from your current shell."
	echo "Use source $cmd  or  . ./$cmd"
	exit 1
}

if [ $# -eq 0 ]; 
then
	echo "No parameter provided to the script"
fi;

export YOCTO_LAYER_DIR=$PWD
echo $YOCTO_LAYER_DIR

buildDir="../rpi-build$(date +'-%Y-%m-%d')"

if [ -z "$1" ];
then
	echo "No build directory specified using " $buildDir

	source $YOCTO_LAYER_DIR/poky/oe-init-build-env $buildDir

	cp -v $YOCTO_LAYER_DIR/meta-rpi-home/conf/local.conf-sample conf/local.conf
	cp -v $YOCTO_LAYER_DIR/meta-rpi-home/conf/bblayers.conf-sample conf/

	sed "s|##OEROOT##|$YOCTO_LAYER_DIR|g" conf/bblayers.conf-sample > conf/bblayers.conf

	rm conf/bblayers.conf-sample

	echo ""
	echo "Starting the rpi-home-image build in $buildDir"
	echo ""
	bitbake rpi-home-image
else
	buildDir=$1
	echo ""
	echo "Build directory specified: " $buildDir
	source $YOCTO_LAYER_DIR/poky/oe-init-build-env $buildDir

	echo ""
	echo "You can now start the build in $buildDir by running \"bitbake rpi-home-image\""
	echo "Please take care to copy the configuration file samples to the $buildir/conf if you haven't done it already!"
	echo ""
fi
