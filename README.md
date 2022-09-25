# graphql-java-datetime

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/tailrocks/graphql-java-datetime/blob/master/LICENSE) 
[![Latest Release](https://img.shields.io/maven-central/v/tailrocks/graphql-java-datetime)](https://maven-badges.herokuapp.com/maven-central/tailrocks/graphql-java-datetime/)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/tailrocks/graphql-java-datetime/pulls)

GraphQL ISO Date is a set of RFC 3339 compliant date/time scalar types to be used with
[graphql-java](https://github.com/graphql-java/graphql-java).


# Summary

A set of ISO 33601, RFC 3339 compatible date time scalars for GraphQL Java implementation ([graphql-java](https://github.com/graphql-java/graphql-java)), as well as starters for
- GraphQL Java Kickstart [graphql-java-kickstart](https://www.graphql-java-kickstart.com/spring-boot/)
- Netflix DGS Framework [dgs-framework](https://netflix.github.io/dgs/)
- Spring GraphQL [spring-graphql](https://spring.io/projects/spring-graphql)

# Serialization

**java.util.Date**, **java.time.LocalDate**, **java.time.LocalDateTime**

| Format                       | JSON String              |
|:-----------------------------|:-------------------------|
| yyyy-MM-dd'T'HH:MM:ss.SSS'Z' | 2017-07-09T13:14:45.947Z |
| yyyy-MM-dd'T'HH:MM:ss'Z'     | 2017-07-09T11:54:42Z     |
| yyyy-MM-dd'T'HH:MM:ss        | 2017-07-09T11:54:42      |
| yyyy-MM-dd                   | 2017-07-09               |

**java.time.LocalTime**

| Format       | JSON String  |
|:-------------|:-------------|
| HH:MM:ss.SSS | 17:59:59.129 |
| HH:MM:ss     | 17:59:59     |
| HH:MM        | 17:59        |

**java.time.Duration**

| JSON String      |
|:-----------------|
| PT1H30M          |
| P1DT3H30M        |
| P3Y6M4DT12H30M5S |

# Usage

## Spring Boot

This library supports the following popular graphql-java framework:
- GraphQL Java Kickstart [graphql-java-kickstart](https://www.graphql-java-kickstart.com/spring-boot/)
- Netflix DGS Framework [dgs-framework](https://netflix.github.io/dgs/)
- Spring GraphQL [spring-graphql](https://spring.io/projects/spring-graphql)

Add one of the following starters according to your project.

### Installation

#### Maven

Add the following to your `pom.xml`:

for **GraphQL Java Kickstart**:

*Note: requires graphql-java:17.x until graphql-java-kickstart:graphql-spring-boot:13.x is released*

```xml
<dependency>
    <groupId>com.tailrocks.graphql</groupId>
    <artifactId>graphql-datetime-kickstart-spring-boot-starter</artifactId>
    <version>5.0.1</version>
</dependency>
```

for **Netflix DGS**:

```xml
<dependency>
    <groupId>com.tailrocks.graphql</groupId>
    <artifactId>graphql-datetime-dgs-starter</artifactId>
    <version>5.0.1</version>
</dependency>
```

for **Spring GraphQL**:

```xml
<dependency>
    <groupId>com.tailrocks.graphql</groupId>
    <artifactId>graphql-datetime-spring-boot-starter</artifactId>
    <version>5.0.1</version>
</dependency>
```

#### Gradle

Add the following to your `build.gradle`:

for **GraphQL Java Kickstart (Spring Boot)**:

```groovy
implementation("com.tailrocks.graphql:graphql-datetime-kickstart-spring-boot-starter:5.0.1")
```

for **DGS**:

```groovy
implementation("com.tailrocks.graphql:graphql-datetime-dgs-starter:5.0.1")
```

for **Spring GraphQL**:

```groovy
implementation("com.tailrocks.graphql:graphql-datetime-spring-boot-starter:5.0.1")
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

# java.time.YearMonth implementation
scalar YearMonth

# java.time.Duration implementation
scalar Duration
```

You can rename the scalar however you want by simply adding the following properties to your application.yaml:

```yaml
graphql:
  datetime:
    scalars:
      date:
        scalar-name: MyDate
      local-date:
        scalar-name: MyLocalDate
      local-date-time:
        scalar-name: MyLocalDateTime
      local-time:
        scalar-name: MyLocalTime
      offset-date-time:
        scalar-name: MyOffsetDateTime
      year-month:
        scalar-name: MyYearMonth
      duration:
        scalar-name: MyDuration
```

A custom format can be set for LocalDate and LocalDateTime only using the following properties in application.yaml

```yaml
graphql:
  datetime:
    scalars:
      local-date:
        format: MM/dd/yyyy
      local-date-time:
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

If using OffsetDateTime in order to present the offset and disable the automatic convertion to UTC from Jackson you
should set to your application.yml the following:

```yaml
spring:
  jackson:
    deserialization:
      adjust-dates-to-context-time-zone: false
```

### Sample

Now you can use these scalars in your application. Here are graphql-datetime spring boot sample applications:

- **webmvc:**
  - dgs: [sample-graphql-datetime-dgs-webmvc](samples/dgs-webmvc)
  - kickstart: [sample-graphql-datetime-kickstart-webmvc](samples/kickstart-webmvc)
  - spring-graphql: [sample-graphql-datetime-spring-boot-webmvc](samples/spring-boot-webmvc)
- **webflux:** 
  - kickstart: [sample-graphql-datetime-kickstart-webflux](samples/kickstart-webflux)

## Bugs

To report any bug, please use the project [Issues](https://github.com/tailrocks/graphql-java-datetime/issues/new) section
on GitHub.

## Contributing

Please contribute using [Github Flow](https://guides.github.com/introduction/flow/). Create a branch, add commits,
and [open a pull request](https://github.com/tailrocks/graphql-java-datetime/compare/).

## License

Copyright © 2017-2022 [Alexey Zhokhov](http://www.zhokhov.com). All rights reserved.

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE](LICENSE) file for details.
