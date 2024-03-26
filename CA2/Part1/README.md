
# Technical Report: Part 1 of CA2 - Gradle Practice

This document serves as a technical guide and report for Part 1 of CA2, focusing on using Gradle for building a simple Java application. The goal is to demonstrate the setup and execution of various Gradle tasks including running a server, executing unit tests, and handling application sources with backup and archive tasks.

## Working in build.gradle
The build.gradle file is a key element in projects that use Gradle as a build automation system. This file is written in the Groovy programming language, which is a dynamic, object-oriented language integrated into the Java ecosystem. Groovy is designed to improve developer productivity by offering a more concise syntax and powerful features compared to Java. Being interoperable with Java, Groovy allows the use of existing Java libraries, as well as enabling the calling of Groovy code from Java and vice versa.

## Brief description of the elements to be used in build.gralde

### Plugins
Define which Gradle plugins are applied to the project. This can include things like Java, Kotlin, Android, among others, to add specific functionalities to the build process.
### Dependencies
Defines the project's dependencies, including third-party libraries, modules, and other configurations necessary for compiling and running the project.
### Repositories
Specifies the repositories from where the project's dependencies will be downloaded, such as Maven Central, JCenter, or local repositories.
### Tasks
Defines custom tasks that can be executed during the build process. These tasks can be set to execute specific code or tasks like compilation, running tests, generating documentation, etc.
### Project Properties
Specific project settings, such as SDK version, build configurations, compilation options, etc.
### JAR (Java Archive) 
Is a crucial component for Java and Kotlin projects, focusing on the packaging stage
### Source Sets
Defines the project's source sets, organizing where Gradle should look for source code files, resources, and tests.

These are just a few of the main elements that can be found in a build.gradle file, but the exact structure and elements present will vary greatly depending on the type of project, the technologies used, and the specific build needs. Gradle is a very flexible and powerful tool, allowing a wide range of customizations and configurations to automate virtually any aspect of the software development process.

### 1. Understand Gradle Project Structure

A typical Gradle project structure includes:
- **`build.gradle`**: The build configuration script.
- **`gradlew` and `gradlew.bat`**: Wrapper scripts for Unix and Windows.
- **`gradle/wrapper/gradle-wrapper.jar` and `gradle-wrapper.properties`**: Gradle Wrapper support files.
- **`src/main/java`**: Directory for Java source files.
- **`src/test/java`**: Directory for test source files.

### 2. Initialize a New Gradle Project

Create a new Gradle project by running:
```bash
gradle init
```

### 3. Writing the Build Script (`build.gradle`)

Configure your project in the `build.gradle` file, including dependencies, plugins, and tasks.

### 4. Adding Dependencies

Add project dependencies in the `build.gradle` file within the `dependencies` block. Example:
```groovy
dependencies {
    implementation group: 'org.apache.logging.log4j', name: 'log4j-api', version: '2.11.2'
}
```

### 5. Building an Project

Build your project with:
```bash
gradle build
```

### 6. Running Your Application

If using the `application` plugin, run your application with:
```bash
gradle run
```

### 7. Using the Gradle Wrapper

Use the Gradle Wrapper to ensure consistent Gradle usage. Run tasks with `./gradlew` instead of `gradle`:
```bash
./gradlew build
```

# Start of the assignment

Clone the example application repository:
```bash
git clone https://bitbucket.org/pssmatos/gradle_basic_demo/
cd gradle_basic_demo
```

## Add a task to start the ChatServer
To define a new task in the `build.gradle` file that uses the `JavaExec` task type. This task type allows to execute a Java application as part of your build process.
```groovy
task runServer(type: JavaExec) {
    // Specify the main class to execute
    mainClass.set('basic_demo.ChatServerApp')
    // Pass arguments to the main class if needed
    args '59002'
    // Use the runtime classpath of your application
    classpath = sourceSets.main.runtimeClasspath
}
```
This task definition tells Gradle to execute the main method in the ChatServerApp class. The args line passes a single argument to your application, which is the server port.

Run the server using Gradle:
```bash
./gradlew runServer
```
This command tells Gradle to execute the runServer task, which in turn starts your chat server application.


## Adding Unit Tests
Include JUnit 4.12 in your `build.gradle` under dependencies
`dependencies` in the build.gradle its already created, so we only need to add 
```groovy

    testImplementation 'junit:junit:4.12'

```
Create a test class `AppTest.java` in `src/test/java`:


Run the tests:
```bash
./gradlew test
```
This command compiles and runs all tests found in the src/test/java directory. If the tests pass, we'll see a BUILD SUCCESSFUL message. If a test fails, Gradle will report the failure, and we can check the detailed test reports usually found in `build/reports/tests/test/index.html` for more information.

## Creating a Backup of Source Files

To add a new task of type `Copy` to Gradle build script for backing up the source files of the application, we'll need to define a task that specifies the source directory (`src`) and the destination directory (e.g., `backup`).

```groovy
task backupSources(type: Copy) {
    description = 'Backs up the source files to a backup directory.'
    from 'src' // specifies the source directory to copy from
    into 'backup' // specifies the destination directory to copy to
}
```
This task, when executed, will copy the entire contents of the src directory into a new directory named backup at the root of the project. If the backup directory does not exist, Gradle will automatically create it.

Execute the task:
```bash
./gradlew backupSources
```
This command tells Gradle to execute the backupSources task, which copies your source files into the backup directory as specified. It's a simple and effective way to create a backup of the application's source code directly from the Gradle build script.

This task will overwrite the contents of the backup directory each time it's run. If we need to keep incremental backups, adding a timestamp or a version number to the backup directory's name within the task definition.


## Archiving Source Files
To add a new task to Gradle build script that creates a ZIP archive of the application's source code, we can use `Zip` task type. This task will take the contents of the `src` folder and package them into a ZIP file.

```groovy
task zipSources(type: Zip) {
    description = 'Archives the source files into a zip file.'
    archiveBaseName.set('application-sources') // Sets the base name of the archive
    destinationDirectory.set(file("$buildDir/archives")) // Sets the destination directory for the zip

    from 'src' // Specifies the source directory to include in the zip
}
```

In this task definition:

archiveBaseName.set('application-sources') sets the base name of the generated ZIP file. We can customize 'application-sources' to whatever name we prefer for the ZIP file.
destinationDirectory.set(file("$buildDir/archives")) specifies the destination directory where the ZIP file will be created. In this case, it uses the build/archives directory within the project's build directory, but we can adjust this path as needed.
from 'src' indicates that the contents of the src directory should be included in the ZIP archive.

Run the task:
```bash
./gradlew zipSources
```

After running this command, we'll find the ZIP file named `application-sources.zip` in the build/archives directory of the project

## Code already in build.gradle

```groovy
plugins {
    id 'application'
}
```
This section declares that the project uses the `application` plugin. This plugin facilitates creating an executable JVM application.

```groovy
repositories {
    mavenCentral()
}
```
Specifies that project dependencies will be fetched from Maven Central.

```groovy
dependencies {
    // Log4J for logging
    implementation group: 'org.apache.logging.log4j', name: 'log4j-api', version: '2.11.2'
}
```
`implementation`: This configuration specifies that log4j-api is a compile-time dependency for the source code. It's required for compiling and running the application.
`group: 'org.apache.logging.log4j', name: 'log4j-api', version: '2.11.2'`: Specifies the dependency details, including the group ID, artifact ID, and version.

```groovy
mainClassName = 'basic_demo.App'
description = "A trivial Gradle build"
version = "0.1.0"
```
Sets the main class, project description, and version.

### JAR Configuration
```groovy
jar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    manifest {
        attributes "Main-Class": "basic_demo.App"
    }
    from {
        configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) }
    }
}
```
`jar`: This block configures the jar task, which packages the compiled classes and resources into a JAR file.
`duplicatesStrategy = DuplicatesStrategy.INCLUDE`: Determines how to deal with duplicate files when creating the JAR. `INCLUDE` means duplicates are included in the JAR.
`manifest`: Configures the JAR file's manifest.
`attributes "Main-Class": "basic_demo.App":` Specifies the main class in the manifest. This is used by the JVM to identify the entry point of the application when the JAR is executed.
`from:` Specifies the source files to include in the JAR.
`configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) }`: Includes all runtime dependencies in the JAR, ensuring that all necessary classes and resources are packaged together.

## Conclusion

Following this guide, we've demonstrated how to perform essential tasks with Gradle in a Java project, including running the server, executing unit tests, and managing source code backups and archives. This report not only serves as documentation for the assignment but also as a practical tutorial on Gradle's capabilities and usage in a simple Java application.
