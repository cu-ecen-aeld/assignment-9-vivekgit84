#!/bin/sh

INITSCRIPTS_DIR=/etc/aesdchar_startup

case $1 in
    start)
        $INITSCRIPTS_DIR/aesdchar_load
    ;;
    stop)
        $INITSCRIPTS_DIR/aesdchar_unload
    ;;
esac


