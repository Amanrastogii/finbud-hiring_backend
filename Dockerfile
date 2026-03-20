FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/finbud-hiring-0.0.1-SNAPSHOT.jar app.jar
RUN mkdir -p uploads
EXPOSE 8080

# 'prod' profile activates application-prod.properties automatically
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]