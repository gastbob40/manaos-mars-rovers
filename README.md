# Manaos Mars Rovers Project

This project uses Quarkus as framework, and Java as programming language.

A basic CI pipeline is provided, using GitHub Actions. It runs the tests and upload the code coverage to artifacts.

# Usage

## Running the application in dev mode

You can run your application in dev mode using:

```shell script
mvn quarkus:dev
```

Or by using this command if you don't have maven installed:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  You can access a swagger of the api at http://localhost:8080/q/swagger-ui/.

## Packaging and running the application

The application can be packaged using:

```shell script
mvn package -Dquarkus.package.type=uber-jar
```

Or by using this command if you don't have maven installed:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
mvn package -Pnative
```

Or by using this command if you don't have maven installed:

```shell script
./mvnw package -Pnative
```

You can then execute your native executable with: `./target/manaos-mars-rovers-1.0-SNAPSHOT-runner`
