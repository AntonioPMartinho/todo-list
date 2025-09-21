
FROM eclipse-temurin:24-jdk AS build
WORKDIR /app


RUN apt-get update && apt-get install -y maven


COPY . .
RUN mvn clean package


FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar


ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
