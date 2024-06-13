## Jenkins Pipeline for Gradle Basic Demo Project

This README provides instructions for setting up a Jenkins pipeline for the "gradle basic demo" project. The goal is to create a simple pipeline with specified stages: Checkout, Assemble, Test, and Archive.

### Installing Jenkins when using Docker

1. Use the following command:
```
docker run -d -p 8080:8080 -p 50000:50000 -v jenkins-data:/var/jenkins_home --name=jenkins
jenkins/jenkins:lts-jdk11
```

1. Open the Jenkins configuration file. The default location is `/etc/default/jenkins` on Linux systems.

2. Find the line that starts with `HTTP_PORT`.

3. Change the port number from 8080 to 9090. For example:

   ```bash
   HTTP_PORT=9090
   ```

4. Save the file and restart Jenkins for the changes to take effect.

   ```bash
   sudo systemctl restart jenkins
   ```
### Jenkins Pipeline Configuration

1. Open Jenkins in a web browser using the URL `http://localhost:9090` (or the port you specified).
2. Click on the "New Item" link on the left side of the Jenkins dashboard.
3. Enter a name for the new item (e.g., "gradle-basic-demo-pipeline") and select "Pipeline" as the type.
4. Click "OK" to create the new pipeline.
5. In the pipeline configuration page, scroll down to the "Pipeline" section.
6. In the "Definition" dropdown, select "Pipeline script from SCM." or "Pipeline script" if you want to write the pipeline script directly in the Jenkins UI.
7. If you selected "Pipeline script from SCM," choose "Git" as the SCM.
8. Enter the repository URL.
9. Click "Save" to save the pipeline configuration.

### Jenkinsfile (Pipeline Script)

The Jenkinsfile contains the pipeline script that defines the stages and steps for the pipeline. The script is written in Groovy and is stored in the root directory of the project.

```groovy
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {

                git url: 'https://github.com/LloydCosta87/devops-23-24-JPE-1231838.git', branch: 'main'
            }
        }
        stage('Assemble') {
            steps {

                dir('CA2/Part1/gradle_basic_demo') {

                    bat './gradlew assemble'
                }
            }
        }
        stage('Test') {
            steps {

                dir('CA2/Part1/gradle_basic_demo') {

                    bat './gradlew test'
                }
            }
            post {
                always {

                    junit 'CA2/Part1/gradle_basic_demo/build/test-results/test/*.xml'
                }
            }
        }
        stage('Archive') {
            steps {

                dir('CA2/Part1/gradle_basic_demo') {

                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }
    }
}

```

Build the pipeline to run the stages and steps defined in the Jenkinsfile.



### Running the .jar file

1. Start the Server

   ```bash
   java -cp basic_demo-0.1.0.jar basic_demo.ChatServerApp 9090
   ```

2. Start the Client (in another terminal window)

   ```bash
   java -cp basic_demo-0.1.0.jar basic_demo.ChatClientApp localhost 9090
   ```

3. Start Multiple Clients

   Repeat the client command in multiple terminal windows to simulate multiple users connecting to the chat server.

## Jenkins Pipeline for React-and-spring-data-rest-basic Project

### 1. Installing Docker Pipeline Plugin

1. Open Jenkins and navigate to "Manage Jenkins".
2. Click on "Manage Plugins".
3. Go to the "Available" tab and search for "Docker Pipeline".
4. Select the plugin and click "Install without restart".

### 2. Jenkins Pipeline Configuration (Scripted Pipeline)

```groovy
pipeline {
    agent any

    environment {
        registry = 'lloydcosta/react-and-spring-data'
        registryCredential = 'dockerhub_credentials'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/LloydCosta87/devops-23-24-JPE-1231838.git', branch: 'main'
            }
        }
        stage('Clean Node Modules') {
            steps {
                dir('CA2/Part2/react-and-spring-data-rest-basic') {
                    bat 'rmdir /s /q node_modules || exit 0'
                }
            }
        }
        stage('Install Node.js Dependencies') {
            steps {
                dir('CA2/Part2/react-and-spring-data-rest-basic') {
                    bat 'npm install --unsafe-perm'
                }
            }
        }
        stage('Run Webpack') {
            steps {
                dir('CA2/Part2/react-and-spring-data-rest-basic') {
                    bat 'npx webpack --config webpack.config.js'
                }
            }
        }
        stage('Assemble') {
            steps {
                dir('CA2/Part2/react-and-spring-data-rest-basic') {
                    bat './gradlew assemble'
                }
            }
        }
        stage('Test') {
            steps {
                dir('CA2/Part2/react-and-spring-data-rest-basic') {
                    bat './gradlew test'
                }
            }
            post {
                always {
                    junit 'CA2/Part2/react-and-spring-data-rest-basic/build/test-results/test/*.xml'
                }
            }
        }
        stage('Javadoc') {
            steps {
                dir('CA2/Part2/react-and-spring-data-rest-basic') {
                    bat './gradlew javadoc'
                }
            }
            post {
                always {
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'CA2/Part2/react-and-spring-data-rest-basic/build/docs/javadoc',
                        reportFiles: 'index.html',
                        reportName: 'Javadoc'
                    ])
                }
            }
        }
        stage('Archive') {
            steps {
                dir('CA2/Part2/react-and-spring-data-rest-basic') {
                    archiveArtifacts artifacts: 'build/libs/*.war', fingerprint: true
                }
            }
        }
        stage('Build Image') {
            steps {
                dir('CA2/Part2/react-and-spring-data-rest-basic') {
                    script {
                        def dockerImage = "${env.registry}:${env.BUILD_NUMBER}"
                        withCredentials([usernamePassword(credentialsId: "${env.registryCredential}", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                            bat 'echo %DOCKER_PASSWORD% | docker login -u %DOCKER_USERNAME% --password-stdin'
                            bat "docker build -t ${dockerImage} ."
                        }
                    }
                }
            }
        }
       stage('Publish Image') {
          steps {
             echo 'Building and pushing Docker image...'
             dir('CA2/Part2/react-and-spring-data-rest-basic') {
                script {
                   def appImage = docker.build("lloydcosta/react-and-spring-data:${env.BUILD_NUMBER}", '-H tcp://localhost:2375')
                   docker.withRegistry('https://registry.hub.docker.com', "${registryCredential}") {
                      appImage.push()
                      echo "Building image with tag: lloydcosta/react-and-spring-data:${env.BUILD_NUMBER}"
                   }
                }
             }
          }
       }
    }
}
```

### 3. Building the Docker Image

```bash
docker run -p 8080:8080 lloydcosta/react-and-spring-data-rest-basic
```

### 4. Accessing the Application

Open a web browser and navigate to `localhost:8080/basic-0.0.1-SNAPSHOT` to access the application.



