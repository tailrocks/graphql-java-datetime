#!/usr/bin/env bash
ABSOLUTE_PATH=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
cd "${ABSOLUTE_PATH}" || exit

set -e

./graphql-datetime-dgs-autoconfigure/publish-sonatype-release.sh
./graphql-datetime-dgs-starter/publish-sonatype-release.sh
./graphql-datetime-kickstart-spring-boot-starter/publish-sonatype-release.sh
./graphql-datetime-spring-boot-autoconfigure/publish-sonatype-release.sh
./graphql-datetime-spring-boot-common/publish-sonatype-release.sh
./graphql-datetime-spring-boot-starter/publish-sonatype-release.sh
./graphql-java-datetime/publish-sonatype-release.sh
