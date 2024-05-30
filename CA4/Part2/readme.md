# CA4 - Part 2: Containerized Environment with Docker

## Introduction

This assignment involves setting up a containerized environment using Docker to execute the Gradle version of the Spring Basic Tutorial application. The solution will utilize Docker Compose to create two services/containers: one for running Tomcat and the Spring application (`web`), and the other for executing the H2 server database (`db`).

## Docker Configuration

### Dockerfile for `db` Container

This Dockerfile sets up the H2 database server. Should be saved in the `db` directory CA4/Part2/db
The H2 database is downloaded and started in the container. The database is exposed on port 8082 for the web application to connect to.

```Dockerfile
FROM gradle:jdk17

RUN apt-get update && \
  apt-get install -y openjdk-17-jdk-headless && \
  apt-get install unzip -y && \
  apt-get install wget -y

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app/

# Download H2 Database and run it
RUN wget https://repo1.maven.org/maven2/com/h2database/h2/2.2.224/h2-2.2.224.jar -O /opt/h2.jar

EXPOSE 8082
EXPOSE 9092

# Start H2 Server
CMD ["java", "-cp", "/opt/h2.jar", "org.h2.tools.Server", "-tcp", "-tcpAllowOthers", "-tcpPort", "9092", "-web", "-webAllowOthers", "-webPort", "8082", "-ifNotExists"]

```
### Dockerfile for `web` Container

This Dockerfile sets up the Tomcat server and deploys the Spring application. Should be saved in the `web` directory CA4/Part2/web
The Spring application JAR file is copied to the container, and the application is started on port 8080.

```Dockerfile
# Use the official Tomcat image
FROM gradle:jdk17

# Copy the WAR file to the webapps directory
COPY react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.jar"]
```
### Docker Compose Configuration

The `docker-compose.yml` file defines the services for the `web` and `db` containers. Should be saved in the project directory CA4/Part2
The `web` service depends on the `db` service and sets the environment variables for the Spring application to connect to the H2 database.

```yaml
version: '3'
services:

   db:
      image: lloydcosta/chatserver/db
      build:
         context: ./db
         dockerfile: Dockerfile
      volumes:
         - db_data:/opt/h2/data
      ports:
         - "9092:9092"
         - "8082:8082"


   web:
      image: lloydcosta/chatserver/web
      build:
         context: ./web
         dockerfile: Dockerfile
      ports:
         - "8080:8080"
      depends_on:
         - db

volumes:
   db_data:

```

### Instructions to Build and Run the Docker Image

1. **Build the Docker Images**

    In the terminal, navigate to the project directory CA4/Part2 and run the following command to build the Docker images:

    ```sh
    docker-compose build
    ```
   
2. **Run the Docker Containers**

    After building the images, run the Docker containers using the following command:

    ```sh
    docker-compose up
    ```
   
3. **Access the Application**

    Open a web browser and navigate to `http://localhost:8080/basic-0.0.1-SNAPSHOT/` to access the Spring application.
    You can also access the H2 database console at `http://localhost:8082` and connect to the database `jpadb
   ` with the JDBC URL `jdbc:h2:tcp://db:9092/~/jpadb`.

### Publishing the Docker Images to Docker Hub

To publish the Docker images to Docker Hub, follow these steps:

1. Log in to Docker Hub using the following command:

    ```sh
    docker login
    ```
   
2. Tag the images with your Docker Hub username and push them to Docker Hub:

    ```sh
   # Tag the db image and push it to Docker Hub
    docker tag lloydcosta/chatserver/db lloydcosta/chatserver/db
    docker push lloydcosta/chatserver/db
    # Tag the web image and push it to Docker Hub
    docker tag lloydcosta/chatserver/web lloydcosta/chatserver/web
    docker push lloydcosta/chatserver/web
    ```

### Tag tge repository 
    
```sh
    git tag ca4-part2
    git push origin ca4-part2
```

   



