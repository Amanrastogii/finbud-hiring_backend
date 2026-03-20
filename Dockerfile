# ── Stage 1: Build the JAR using Maven ────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first so Maven dependencies are cached between builds
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests -q

# ── Stage 2: Run the JAR on a slim Java image ──────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR from stage 1
COPY --from=build /app/target/finbud-hiring-0.0.1-SNAPSHOT.jar app.jar

# Create uploads directory for resumes
RUN mkdir -p uploads

# Render sets PORT automatically — Spring reads it via ${PORT:8080}
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]