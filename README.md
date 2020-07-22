# graphql-java-datetime &middot; [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/donbeave/graphql-java-datetime/blob/master/LICENSE) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/donbeave/graphql-java-datetime/pulls)
GraphQL ISO Date is a set of RFC 3339 compliant date/time scalar types to be used with [graphql-java](https://github.com/graphql-java/graphql-java).

[![Build Status](https://travis-ci.org/donbeave/graphql-java-datetime.svg?branch=master)](https://travis-ci.org/donbeave/graphql-java-datetime)
[![Latest Dev Build](https://api.bintray.com/packages/donbeave/maven/graphql-java-datetime/images/download.svg)](https://bintray.com/donbeave/maven/graphql-java-datetime/_latestVersion)

# Summary

A set of ISO 33601, RFC 3339 compatible date time scalars for GraphQL Java implementation ([graphql-java](https://github.com/graphql-java/graphql-java)) and GraphQL Java Kickstart ([com.graphql-java-kickstart:graphql-spring-boot-starter](https://github.com/graphql-java-kickstart/graphql-spring-boot)).

# Serialization

**java.util.Date**, **java.time.LocalDate**, **java.time.LocalDateTime**

| Format                       | JSON String              |
|:-----------------------------|:-------------------------|
| yyyy-MM-dd'T'HH:MM:ss.SSS'Z' | 2017-07-09T13:14:45.947Z |
| yyyy-MM-dd'T'HH:MM:ss'Z'     | 2017-07-09T11:54:42Z     |
| yyyy-MM-dd'T'HH:MM:ss        | 2017-07-09T11:54:42      |
| yyyy-MM-dd                   | 2017-07-09               |

**java.time.LocalTime**

| Format       | JSON String  |
|:-------------|:-------------|
| HH:MM:ss.SSS | 17:59:59.129 |
| HH:MM:ss     | 17:59:59     |
| HH:MM        | 17:59        |

# Usage

## Spring Boot

Add `graphql-datetime-spring-boot-starter` or `graphql-datetime-spring-boot-starter-webflux` starter (according to your spring boot stack) to your project first.

### Installation

#### Maven

Add folowing to your `pom.xml`:

for **Spring MVC**:

```xml
<dependency>
  <groupId>com.zhokhov.graphql</groupId>
  <artifactId>graphql-datetime-spring-boot-starter</artifactId>
  <version>2.1.0</version>
</dependency>
```

or 

for **Spring WebFlux**:

```xml
<dependency>
  <groupId>com.zhokhov.graphql</groupId>
  <artifactId>graphql-datetime-spring-boot-starter-webflux</artifactId>
  <version>2.1.0</version>
</dependency>
```

#### Gradle

Add folowing to your `build.gradle`:

for **Spring MVC**:

```groovy
implementation("com.zhokhov.graphql:graphql-datetime-spring-boot-starter:2.1.0")
```

or 

for **Spring WebFlux**:

```groovy
implementation("com.zhokhov.graphql:graphql-datetime-spring-boot-starter-webflux:2.1.0")
```

### Scalars

Add these scalars to your `.graphqls` schema file:

```graphql
# java.util.Date implementation
scalar Date

# java.time.LocalDate implementation
scalar LocalDate

# java.time.LocalDateTime implementation
scalar LocalDateTime

# java.time.LocalTime implementation
scalar LocalTime

# java.time.OffsetDateTime implementation
scalar OffsetDateTime 

# YearMonth implementation
scalar java.time.YearMonth
```

You can rename the scalar however you want with by simply adding the following properties to you application.yaml:

```yaml
graphql:
  datetime:
    scalars:
      date:
        scalarName: MyDate
      localDate:
        scalarName: MyLocalDate
      localDateTime:
        scalarName: MyLocalDateTime
      localTime:
        scalarName: MyLocalTime
      offsetDateTime:
        scalarName: MyOffsetDateTime
      yearMonth:
        scalarName: MyYearMonth
```

A custom format can be set for LocalDate and LocalDateTime only using the following properties in application.yaml

```yaml
graphql:
  datetime:
    scalars:
      localDate:
        format: MM/dd/yyyy
      localDateTime:
        format: yyyy-MM-dd'T'HH:mm:ss
```

You can enable automatic zone conversion by adding the following property to your application.yaml. This will
automatically convert between UTC and the default TimeZone for `LocalDateTime`:

```yaml
graphql:
  datetime:
    scalars:
      zone-conversion-enabled: true
```

If using OffsetDateTime in order to present the offset and disable the automatic convertion to UTC from Jackson you should
set to your application.yml the following:

```yaml
spring:
  jackson:
    deserialization:
      adjust-dates-to-context-time-zone: false
```

### Sample

Now you can use these scalars in your application. Here is graphql-datetime spring boot sample applications:
 - mvc: [graphql-datetime-sample-app](graphql-datetime-sample-app/)
 - weblux: [graphql-datetime-sample-app-webflux](graphql-datetime-sample-app-webflux/)

## Bugs

To report any bug, please use the project [Issues](https://github.com/donbeave/graphql-java-datetime/issues/new) section on GitHub.

## Contributing

Please contribute using [Github Flow](https://guides.github.com/introduction/flow/). Create a branch, add commits, and [open a pull request](https://github.com/donbeave/graphql-java-datetime/compare/).

## License

Copyright © 2017-2020 [Alexey Zhokhov](http://www.zhokhov.com). All rights reserved.

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE](LICENSE) file for details.
