# CA4, Part 1 - Containers with Docker

## Description

This part of the assignment involves creating Docker images and running containers using the chat application developed in CA2. The goal is to package and run the chat server in a Docker container.

## Instructions to Build and Run the Docker Image

### Version 1: Building the Chat Server inside the Dockerfile

### Steps

1. **Create the Dockerfile**

   Create a file named `Dockerfile` in the project directory with the following content:

   ```dockerfile
   # Use a Gradle image with JDK 17 to build the application
   FROM gradle:jdk17 AS builder

   # Set the working directory for the build
   WORKDIR /CA4/Part1

   # Clone the repository
   RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git

   # Set the working directory to the cloned repository
   WORKDIR /CA4/Part1/gradle_basic_demo

   # Ensure the Gradle wrapper has the correct permissions
   RUN chmod +x gradlew

   # Build the application
   RUN ./gradlew build

   # Use a slim JRE image for the runtime
   FROM eclipse-temurin:17-jre

   # Set the working directory
   WORKDIR /app

   # Copy the built JAR file from the builder stage
   COPY --from=builder /CA4/Part1/gradle_basic_demo/build/libs/basic_demo-0.1.0.jar /app/basic_demo-0.1.0.jar

   # Expose the port the server will run on
   EXPOSE 59001

   # Set the entry point to run the server
   ENTRYPOINT ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
   ```

2. **Build the Docker Image**

   In the terminal, navigate to the directory where the `Dockerfile` is located and run the following command to build the Docker image:

   ```sh
   docker build -t lloydcosta/chatserver:version1 .
   ```

3. **Publish the Image to Docker Hub**

   To publish the Docker image to Docker Hub, first log in to Docker Hub using the following command:

   ```sh
   docker login
   ```

   Then, tag the image with your Docker Hub username and push it to Docker Hub:

   ```sh
   docker tag lloydcosta/chatserver:version1 lloydcosta/chatserver:version1
   docker push lloydcosta/chatserver:version1
   ```

4. **Run the Docker Container**

   After building the image, run the Docker container with the following command:

   ```sh
   docker run -p 59001:59001 lloydcosta/chatserver:version1
   ```

### Version 2: Building the Chat Server in The Host Machine (and copying the JAR file to the Docker image)

### Steps

1. **Complie the Chat Server**

   Compile the chat server application using the following command in the project directory:

   ```sh
   ./gradlew build
   ```

   This will generate a JAR file in the `build/libs` directory.

2. **Create the Dockerfile**

   Create a file named `Dockerfile` **in the project directory** with the following content:

   ```dockerfile
   # Use a slim JRE image for the runtime
   FROM eclipse-temurin:17-jre

   # Set the working directory
   WORKDIR /app

   # Copy the JAR file from the host machine to the Docker image
   COPY basic_demo-0.1.0.jar /app/basic_demo-0.1.0.jar

   # Expose the port the server will run on
   EXPOSE 59001

   # Set the entry point to run the server
   ENTRYPOINT ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
   ```

3. **Build the Docker Image**

   In the terminal, navigate to the directory where the `Dockerfile` is located and run the following command to build the Docker image:

   ```sh
   docker build -t lloydcosta/chatserver:version2 .
   ```

4. **Publish the Image to Docker Hub**

   To publish the Docker image to Docker Hub, first log in to Docker Hub using the following command:

    ```sh
    docker login
    ```

   Then, tag the image with your Docker Hub username and push it to Docker Hub:

    ```sh
    docker tag lloydcosta/chatserver:version2 lloydcosta/chatserver:version2
    docker push lloydcosta/chatserver:version2
    ```

5. **Run the Docker Container**

   After building the image, run the Docker container with the following command:

    ```sh
    docker run -p 59001:59001 lloydcosta/chatserver:version2
    ```
   
   

    
