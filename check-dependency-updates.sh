#!/usr/bin/env bash
ABSOLUTE_PATH=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
cd "${ABSOLUTE_PATH}" || exit

set -e

./gradlew dependencyUpdates -Drevision=release --info --parallel --continue --no-daemon ${GRADLE_EXTRA_ARGS}
