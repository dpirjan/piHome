#!/bin/sh

export XDG_RUNTIME_DIR=/tmp
export vblank_mode=0 ## disable the VSync
export QT_QPA_PLATFORM=eglfs

vblank_mode=0 /usr/share/qmlbench/qmlbench --decide-fps --fps-override 60

vblank_mode=0 /usr/share/qmlbench/qmlbench benchmark/changes 

vblank_mode=0 /usr/share/qmlbench/qmlbench benchmark/creation

vblank_mode=0 /usr/share/qmlbench/qmlbench benchmark/gputhroughput

vblank_mode=0 /usr/share/qmlbench/qmlbench benchmark/images

