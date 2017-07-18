# graphql-java-datetime
GraphQL ISO Date is a set of RFC 3339 compliant date/time scalar types to be used with [graphql-java](https://github.com/graphql-java/graphql-java).

[![Build Status](https://travis-ci.org/donbeave/graphql-java-datetime.svg?branch=master)](https://travis-ci.org/donbeave/graphql-java-datetime)
[![Latest Dev Build](https://api.bintray.com/packages/donbeave/maven/graphql-java-datetime/images/download.svg)](https://bintray.com/donbeave/maven/graphql-java-datetime/_latestVersion)

# Summary

A set of ISO 33601, RFC 3339 compatible date time scalars for GraphQL Java implementation ([graphql-java](https://github.com/graphql-java/graphql-java)).

# Serialization

**java.util.Date**, **java.time.LocalDate**, **java.time.LocalDateTime**

| Format                       | JSON String              |
|:-----------------------------|:-------------------------|
| yyyy-MM-dd'T'HH:MM:ss.SSS'Z' | 2017-07-09T13:14:45.947Z |
| yyyy-MM-dd'T'HH:MM:ss'Z'     | 2017-07-09T11:54:42Z     |
| yyyy-MM-dd'T'HH:MM:ss        | 2017-07-09T11:54:42'     |
| yyyy-MM-dd                   | 2017-07-09               |

**java.time.LocalTime**

| Format       | JSON String  |
|:-------------|:-------------|
| HH:MM:ss.SSS | 17:59:59.129 |
| HH:MM:ss     | 17:59:59     |
| HH:MM        | 17:59        |

# Usage

## Spring Boot

Add `graphql-datetime-spring-boot-starter` starter to your project first.

### Installation

#### Maven

Add folowing to your `pom.xml`:

```xml
<dependency>
  <groupId>com.zhokhov.graphql</groupId>
  <artifactId>graphql-datetime-spring-boot-starter</artifactId>
  <version>2017-07-15T18-08-56</version>
</dependency>
```

#### Gradle

Add folowing to your `build.gradle`:

```groovy
compile 'com.zhokhov.graphql:graphql-datetime-spring-boot-starter:2017-07-15T18-08-56'
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
```

### Sample

Now you can use these scalars in your application. Here is a graphql-datetime spring boot sample application, https://github.com/donbeave/graphql-java-datetime/tree/master/graphql-datetime-sample-app.

## Bugs

To report any bug, please use the project [Issues](https://github.com/donbeave/graphql-java-datetime/issues/new) section on GitHub.

## Contributing

Please contribute using [Github Flow](https://guides.github.com/introduction/flow/). Create a branch, add commits, and [open a pull request](https://github.com/donbeave/graphql-java-datetime/compare/).

## License

Copyright © 2017 Alexey Zhokhov. All rights reserved.

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE](LICENSE) file for details.
