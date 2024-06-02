# Use the OpenJDK base image
FROM openjdk:17-jdk-slim

# Add the WAR file to the container
ADD build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /app.war

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app.war"]
