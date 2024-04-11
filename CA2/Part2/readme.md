
# Gradle Conversion Guide

## Step 1: Create New Branch for Gradle Conversion

First, create a new branch in your repository for this part of the assignment. Name it `tut-basic-gradle`.

```shell
git checkout -b tut-basic-gradle
```

## Step 2: Generate Gradle Spring Boot Project

Use [Spring Initializr](https://start.spring.io) to generate a new Gradle project with the specified dependencies (Rest Repositories, Thymeleaf, JPA, H2). 

- **Project:** Gradle Project
- **Language:** Java
- **Spring Boot:** Choose the version specified by your instructor or the latest stable version.
- **Project Metadata:** Fill as required.
- **Dependencies:** Rest Repositories, Thymeleaf, JPA, H2

After filling in the details, generate the project and unzip it into the folder `CA2/Part2/` in your repository.

## Step 3: Replace the `src` Folder

1. **Delete the generated `src` folder** inside `CA2/Part2/`.
   ```shell
   rm -r src
   ```
2. **Copy the `src` folder from the basic tutorial** into `CA2/Part2/`.
3. **Copy `webpack.config.js` and `package.json`** to `CA2/Part2/`.
4. **Delete `src/main/resources/static/built/`** since it will be regenerated.

## Step 4: Configure Gradle for Frontend Management

1. **Add the org.siouan.frontend Gradle plugin** to `build.gradle`:
   ```groovy
   plugins {
       id 'org.siouan.frontend-jdk17' version '8.0.0' 
   }
   ```
   Visit [frontend-gradle-plugin](https://github.com/Siouan/frontend-gradle-plugin) for more details.

2. **Configure the plugin in `build.gradle`**:
   ```groovy
   frontend {
       nodeVersion = '16.20.2'
       assembleScript = 'run build'
       cleanScript = 'run clean'
       checkScript = 'run check'
   }
   ```
3. **Update `package.json`** to include necessary scripts for Webpack:
   ```json
   "scripts": {
       "webpack": "webpack",
       "build": "npm run webpack",
       "check": "echo Checking frontend",
       "clean": "echo Cleaning frontend",
       "lint": "echo Linting frontend",
       "test": "echo Testing frontend"
   }
   ```
## Step 5: Build and Run the Application

1. **Build the project** with Gradle:
   ```shell
   ./gradlew build
   ```
   This will execute frontend tasks and generate the frontend code.

2. **Run the application**:
   ```shell
   ./gradlew bootRun
   ```

## Step 6: Additional Gradle Tasks

1. **Copy generated jar to `dist`**:
   Add this task to `build.gradle`:
   ```groovy
   task copyJar(type: Copy) {
       from 'build/libs'
       into 'dist'
       include '*.jar'
   }
   ```
   Execute it with:
   ```shell
   ./gradlew copyJar
   ```

2. **Delete webpack generated files before cleaning**:
   Add this task to `build.gradle`:
   ```groovy
   clean.dependsOn 'cleanFrontend'
   
   task cleanFrontend(type: Delete) {
       delete 'src/main/resources/static/built'
   }
   ```

## Step 7: Commit, Merge, and Document
git add .
git commit -m "Merge brach whit main . Closes #(nr issue)"

git checkout main
git merge --no-ff tut-basic-gradle

git push origin main


# Project Configuration with Bazel for Java

This guide outlines the basic setup of a Java project using Bazel.

## 1. Initial Bazel Setup

Ensure Bazel is installed on your system. Verify by running `bazel version` in the terminal.

## 2. Create Necessary Directory Structure and Files

Create an empty `WORKSPACE` file in the root directory of your project.

## 3. Configure Project Dependencies

Add your external dependencies in the `WORKSPACE` file:


## 4. Configure Java Project Build

In the directory of your Java source code, create a `BUILD` file:

```python
java_binary(
    name = "your_application_name",
    srcs = glob(["src/main/java/**/*.java"]),
    deps = [
        "@maven//:org_springframework_boot_spring_boot_starter_web",
        "@maven//:org_springframework_boot_spring_boot_starter_data_jpa",
        "@maven//:org_springframework_boot_spring_boot_starter_thymeleaf",
        "@maven//:com_h2database_h2",
        # Add other dependencies here
    ],
    main_class = "com.example.MainClass",
)
```

## 5. Build and Execution

To build your application, execute:

```bash
bazel build //:your_application_name
```

To run:

```bash
bazel run //:your_application_name
```
## Gradle vs bazel_comparison 

### Configuration and Usage Ease

- **Gradle:** Uses a syntax based on Groovy or Kotlin for build file configuration, which can be more intuitive for developers familiar with these languages. The integration with Spring Boot and the initial setup via https://start.spring.io make the initial process quite streamlined.
- **Bazel:** Focuses on performance and scalability, using a dependency graph approach to optimize builds. The initial configuration and project setup can be more complex compared to Gradle, especially for Java Spring Boot projects, where configuring dependencies and plugins might not be as straightforward as in Gradle.

### Dependency Management

- **Gradle:** Offers robust and flexible dependency management, allowing easy integration with Maven and Ivy repositories. This is facilitated by the use of the `build.gradle` file, where dependencies are simply declared.
- **Bazel:** Manages dependencies efficiently to ensure reproducible and fast builds. However, setting up external dependencies, like those of Spring Boot, might require more steps compared to Gradle, as each dependency and its transitives need to be explicitly defined in the WORKSPACE and BUILD files.

### Extensibility

- **Gradle:** Provides a wide range of plugins and the ability to easily create custom tasks, thanks to its flexible scripting language. This allows for great adaptability to different project needs, including frontend development, as demonstrated with the integration of the `org.siouan.frontend` plugin.
- **Bazel:** While highly extensible and allowing for the creation of custom rules and macros, the learning curve for extending its functionality can be steeper. Integration with frontend tools or other specific tasks might not be as direct or well-documented as in the Gradle ecosystem.

### Performance

- **Gradle:** With the introduction of the Daemon and other optimizations, Gradle has significantly improved its performance over time. However, very large projects may still face challenges in terms of speed and efficiency.
- **Bazel:** Stands out in performance, especially for large-scale projects, by reusing artifacts from previous builds and running tasks in parallel whenever possible. This significantly reduces build time compared to other tools.

### Conclusion

For Java projects, especially those using Spring Boot and involving considerable frontend development, **Gradle** may offer a more direct setup and a less steep learning curve, along with a wide range of available plugins. However, for large-scale projects that require performance optimization and highly reproducible builds, **Bazel** presents significant advantages, albeit with a more complex initial setup and a steeper learning curve.


