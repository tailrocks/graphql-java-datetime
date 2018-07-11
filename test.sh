#!/usr/bin/env bash
export TERM="dumb"

printf "\n> \e[1;37mTesting graphql-java-datetime\e[0m\n"

set -e

ABSOLUTE_PATH=$(cd `dirname "${BASH_SOURCE[0]}"` && pwd)
cd ${ABSOLUTE_PATH}

appVersion=$(./version.sh)

printf "\n# Project version \e[1;37m$appVersion\e[0m\n"

rm -rf build

./gradlew --stop
./gradlew clean test --info --stacktrace
