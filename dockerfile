FROM maven

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

ENV PORT=8080
EXPOSE 8080
CMD ["java", "-jar", "target/todo-list-0.0.1-SNAPSHOT.jar"]
