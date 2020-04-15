#!/usr/bin/env bash
ABSOLUTE_PATH=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
cd "${ABSOLUTE_PATH}" || exit

set -e

./gradlew --stop

rm -rf build .gradle

./gradlew --no-configure-on-demand --no-daemon --no-parallel clean dependencies --stacktrace --info ${GRADLE_EXTRA_ARGS}
