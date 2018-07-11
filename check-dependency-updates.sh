#!/usr/bin/env bash

ABSOLUTE_PATH=$(cd `dirname "${BASH_SOURCE[0]}"` && pwd)
cd ${ABSOLUTE_PATH}

./gradlew --no-build-cache --no-configure-on-demand --no-daemon --no-parallel dependencyUpdates -Drevision=release
