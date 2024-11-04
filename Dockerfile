FROM openjdk:17-jdk-slim AS build

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY gradle gradle
COPY gradlew ./
RUN ./gradlew dependencies --no-daemon

COPY src ./src

RUN ./gradlew build --no-daemon

FROM openjdk:17-jdk-slim
COPY --from=build /app/build/libs/test-assignment-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]