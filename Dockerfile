
FROM openjdk:11
WORKDIR /app
COPY ./target/EntitlementService-1.0.0.jar /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "EntitlementService-1.0.0.jar"]
