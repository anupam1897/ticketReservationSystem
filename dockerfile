FROM eclipse-temurin:21
WORKDIR /app
COPY target/ticket-reservation-system-1.0.0.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]
