#!/usr/bin/env bash

export TERM="dumb"

set -e

ABSOLUTE_PATH=$(cd `dirname "${BASH_SOURCE[0]}"` && pwd)
cd ${ABSOLUTE_PATH}

export CACHE_CHANGING_MODULES=false

./gradlew --stop

rm -rf build .gradle

./gradlew --no-build-cache --no-configure-on-demand --no-daemon --no-parallel dependencies
