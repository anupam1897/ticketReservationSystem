FROM eclipse-temurin:21
WORKDIR /app
COPY target/ticket-reservation-system-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
