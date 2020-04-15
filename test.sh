#!/usr/bin/env bash
ABSOLUTE_PATH=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
cd "${ABSOLUTE_PATH}" || exit

set -e

printf "> \e[1;37mTesting graphql-java-datetime\e[0m\n"

GIT_COMMIT_DESC=$(git log --format=%B -n 1)

if [[ ${GIT_COMMIT_DESC} == *"[skip test]"* ]]; then
  echo "Ignore tests"
  exit 0
fi

if [[ ${SKIP_TEST} == "true" ]]; then
  echo "Ignore tests"
  exit 0
fi

./gradlew --stop

rm -rf build .gradle

export SPRING_PROFILES_ACTIVE="test"

./gradlew clean test --info --stacktrace ${GRADLE_EXTRA_ARGS}
