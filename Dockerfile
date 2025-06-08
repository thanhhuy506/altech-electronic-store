# Build stage
FROM eclipse-temurin:21-jdk-jammy as builder

WORKDIR /workspace/app

# Copy Gradle files first for better layer caching
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Build the application
RUN ./gradlew clean build

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built JAR
COPY --from=builder /workspace/app/build/libs/*.jar altech-electronic-store.jar

# Create non-root user
RUN addgroup --system spring && \
    adduser --system --ingroup spring spring && \
    chown -R spring:spring /app
USER spring

# Expose port
EXPOSE 8080

# Run the app with H2 file database
ENTRYPOINT ["java", "-jar", "altech-electronic-store.jar"]