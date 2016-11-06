#!/bin/sh

export XDG_RUNTIME_DIR=/tmp
export vblank_mode=0 ## disable the VSync
export QT_QPA_PLATFORM=eglfs

vblank_mode=0 /usr/share/qmlbench/qmlbench --fullscreen --decide-fps

vblank_mode=0 /usr/share/qmlbench/qmlbench --fullscreen benchmark/changes 

vblank_mode=0 /usr/share/qmlbench/qmlbench --fullscreen benchmark/creation

vblank_mode=0 /usr/share/qmlbench/qmlbench --fullscreen benchmark/gputhroughput

vblank_mode=0 /usr/share/qmlbench/qmlbench --fullscreen benchmark/images
