# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy gradle files
COPY marble-and-arch/gradlew .
COPY marble-and-arch/gradle gradle
COPY marble-and-arch/build.gradle .
COPY marble-and-arch/settings.gradle .

# Copy source code
COPY marble-and-arch/src src

# Build the application
RUN ./gradlew bootJar -x test

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]
