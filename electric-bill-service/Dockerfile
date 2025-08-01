FROM gradle:jdk21 AS builder
WORKDIR /app
COPY build.gradle gradlew gradle/wrapper/ /app/
COPY src /app/src
RUN gradle build --no-daemon

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar","/app/app.jar"]