# Build stage: JDK 24 + Maven instalado manualmente
FROM eclipse-temurin:24-jdk AS build
WORKDIR /app

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Copiar o código e compilar
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage: JDK 24 (não existe JRE separada ainda)
FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render define automaticamente a variável $PORT
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
