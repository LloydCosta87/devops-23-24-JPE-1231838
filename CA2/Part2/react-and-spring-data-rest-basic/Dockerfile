# Use the OpenJDK base image
FROM openjdk:17-jdk-slim

# Add the WAR file to the container
COPY build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war .

EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war"]
