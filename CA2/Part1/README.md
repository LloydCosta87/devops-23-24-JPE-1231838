
# Technical Report: Part 1 of CA2 - Gradle Practice

This document serves as a technical guide and report for Part 1 of CA2, focusing on using Gradle for building a simple Java application. The goal is to demonstrate the setup and execution of various Gradle tasks including running a server, executing unit tests, and handling application sources with backup and archive tasks.

## Environment Setup

Before proceeding, ensure you have the following prerequisites installed:
- Java JDK 8 or above
- Gradle 6.0 or above

Clone the example application repository:
```bash
git clone https://bitbucket.org/pssmatos/gradle_basic_demo/
cd gradle_basic_demo
```

## Task Implementation

### Adding a New Task to Execute the Server

1. **Description**: We added a Gradle task to start the application server.
2. **Steps**:
    - Edit `build.gradle` and add the following task:
    ```groovy
    task runServer(type: JavaExec) {
        main = 'com.example.Main'
        classpath = sourceSets.main.runtimeClasspath
    }
    ```
    - This task specifies the main class and classpath to execute the server.

3. **Justification**: Using `JavaExec` type allows us to execute Java applications directly from Gradle, simplifying development and testing.

### Adding a Simple Unit Test

1. **Description**: Introduced a unit test to validate application functionality.
2. **Steps**:
    - Add JUnit dependency to `build.gradle`:
    ```groovy
    testImplementation 'junit:junit:4.12'
    ```
    - Create a test class in `src/test/java` with your tests.

3. **Justification**: JUnit 4.12 was chosen for compatibility with the existing project setup and its wide adoption in Java projects.

### Executing Unit Tests

- **Description**: Configured Gradle to run unit tests.
- **Command**:
```bash
gradle test
```

### Backup Task

1. **Description**: Added a task to backup source files.
2. **Steps**:
    - Edit `build.gradle` and add:
    ```groovy
    task backupSources(type: Copy) {
        from 'src'
        into 'backup'
    }
    ```
3. **Justification**: This task ensures there's a straightforward way to create backups of the source code, which is vital for preventing data loss.

### Archive Task

1. **Description**: Implemented a task to archive source files into a ZIP file.
2. **Steps**:
    - In `build.gradle`, add:
    ```groovy
    task zipSources(type: Zip) {
        from 'src'
        archiveFileName = 'sourceArchive.zip'
    }
    ```
3. **Justification**: Archiving sources is a common practice for versioning and distribution, and using Gradle's `Zip` task simplifies this process.

### Repository Tagging

- **Description**: Mark the repository with a tag upon completion.
- **Command**:
```bash
git tag ca2-part1
git push origin ca2-part1
```

## Conclusion

Following this guide, we've demonstrated how to perform essential tasks with Gradle in a Java project, including running the server, executing unit tests, and managing source code backups and archives. This report not only serves as documentation for the assignment but also as a practical tutorial on Gradle's capabilities and usage in a simple Java application.
