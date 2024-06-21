FROM openjdk:17-jdk

WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY src src