#!/bin/sh

clear

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
else
	buildDir=$1
	echo "Build directory specified: " $buildDir
fi

source $YOCTO_LAYER_DIR/poky/oe-init-build-env $buildDir

cp -v $YOCTO_LAYER_DIR/meta-rpi-home/conf/local.conf-sample conf/local.conf
cp -v $YOCTO_LAYER_DIR/meta-rpi-home/conf/bblayers.conf-sample conf/

sed "s|##OEROOT##|$YOCTO_LAYER_DIR|g" conf/bblayers.conf-sample > conf/bblayers.conf

rm conf/bblayers.conf-sample

echo "Starting the rpi-home-image build in $buildDir"

bitbake rpi-home-image
