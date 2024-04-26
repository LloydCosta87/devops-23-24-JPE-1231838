# 1) VM Installation Guide

## Create a Host-Only Network
- Go to **File** -> **Host Network Manager** and create a new host-only network by clicking the create button. This will allow you to name your host-only networks within your VM's network configuration.

## Set Up Networking Before Starting the VM
- **Network Adapter 2** should be set as a Host-only Adapter (`vboxnet0`). Check the IP address range of this network (default is 192.168.56.1/24) and assign an IP within this range for your VM’s second adapter.

## Update and Configure the Network Post VM Start
- After starting the VM, log in and update package repositories with `sudo apt update`.
- Install network tools using `sudo apt install net-tools`.
- Edit the network configuration file `/etc/netplan/01-netcfg.yaml` to set up the IP for the second adapter (e.g., 192.168.56.5).

## Install Utilities and Servers
- Install `openssh-server` using `sudo apt install openssh-server` to enable SSH connections.
- Configure SSH to allow password authentication by editing `/etc/ssh/sshd_config` and uncommenting `PasswordAuthentication yes`.
- Restart SSH service with `sudo service ssh restart`.
- Install an FTP server (`vsftpd`) using `sudo apt install vsftpd` and enable write access in the configuration.

## Access the VM Using SSH and FTP
- You can now SSH into your VM using `ssh username@192.168.56.5`, replacing `username` with your VM's user name and `192.168.56.5` with the VM’s IP.
- For FTP, use an application like FileZilla to transfer files to/from the VM using the FTP protocol.

## Reboot the Virtual Machine
After the Guest Additions are installed, reboot the virtual machine to apply the changes:
```bash
sudo shutdown -r now
```
# 2) Clone individual repository inside VM
This guide provides the necessary commands to set up SSH access for GitHub in your Ubuntu virtual machine, ensuring secure and authenticated communications.

## 1. Open SSH Configuration File

```bash
nano ~/.ssh/config
```
**Description**: Opens the SSH configuration file using the nano editor. If the file does not exist, nano will create it. This file is used to define specific SSH client settings for different hosts.

## 2. Add GitHub Configuration to SSH File

```plaintext
Host github.com
    HostName github.com
    User git
    IdentityFile ~/.ssh/id_ed25519
    IdentitiesOnly yes
```
**Description**: Adds configuration for GitHub to the SSH config file. This sets up a specific SSH profile for GitHub connections, specifying the hostname, user, path to the private key, and ensuring that only the specified key is used for this connection.

## 3. Save and Exit Nano

**Commands to use**:
- To save changes: `Ctrl+O`
- To exit nano: `Ctrl+X`

**Description**: Saves any changes made to the file and exits the nano editor. `Ctrl+O` confirms the save, and `Ctrl+X` closes the editor.

## 4. Test SSH Connection with GitHub

```bash
ssh -T git@github.com
```
**Description**: Tests the SSH connection to GitHub to verify that the setup is correct. You should receive a message from GitHub stating that you are authenticated but GitHub does not provide shell access.

## 5. Clone the Repository

```bash
git clone git@github.com:LloydCosta87/devops-23-24-JPE-1231838.git
```
**Description**: Clones the specified GitHub repository using SSH. This command requires that the SSH key be correctly configured and added to your GitHub account to succeed.

# 3.1) Installation and Execution Guide for Spring Boot Project in maven

### To clone and manage your code repositories:
```bash
sudo apt update
sudo apt install git
```

### Java JDK
Needed for both Maven and Gradle to compile and run Java code:
``` bash
sudo apt install openjdk-17-jdk
```

### Maven
Required for building and running the Spring Boot project:
```bash
sudo apt install maven
```

## Build and Execute Spring Boot Project (CA1)
Navigate to the directory of the Spring Boot project:
cd path_to_your_repository/CA1/basic

Build the project using Maven. This will compile the source code and package it into a runnable jar file, including downloading dependencies:
```bash
mvn clean install
```

Run the Spring Boot application:
```bash
mvn spring-boot:run
```
####  The following image shows the successful Maven build.

![build maven sucess](https://github.com/LloydCosta87/devops-23-24-JPE-1231838/assets/147655195/f272945c-b97c-4d5b-a172-aacc5ef6b033)

#### The following image shows the successful build for SpringBoot in maven.
![springBoot com maven](https://github.com/LloydCosta87/devops-23-24-JPE-1231838/assets/147655195/cfbee842-672f-4381-b12a-8c87f0f4a7c5)

# 3.2) Installation and Execution Guide for Spring Boot Project in Gradle
Navigate to the directory of the Gradle project:
```bash
cd path_to_your_repository/Part1/gradle_basic_demo
```
## Encountered Issues Summary

Below is a summary of the issues encountered during the project setup and build process, based on the provided screenshots:

1. **Unsupported Java Version**
   The Gradle build failed due to an incompatible Java version being used, which was not suitable for the project's code or dependencies.

2. **Gradle Daemon Memory Error**
   The Gradle daemon ran out of memory during the build process, indicating insufficient memory allocation for the tasks at hand.

3. **Gradle Build Failures**
   There were multiple failures in the Gradle build process, including 'MODULE_NOT_FOUND' errors, pointing to issues with project dependencies.

4. **Permission Denied for Webpack**
   An attempt to execute `webpack` via an npm script resulted in a permission denied error.

5. **Missing Node.js Modules**
   Webpack failed to locate necessary Node.js modules, implying they were either not installed or missing from the `node_modules` directory.

6. **Webpack Execution Problem**
   The `npm run build` command failed, related to the execution of `webpack`.

7. **Webpack Command Failure**
   Attempts to run the `webpack` command resulted in errors, which could stem from permission issues or incorrect installation.

## To avoid the issues use the following commands

### Gradle Build with Stacktrace
**Command**: `gradle build --stacktrace`  
**Description**: Runs the Gradle build and provides a stack trace in case of failure, which is useful for diagnosing issues.

### Install Project Dependencies
**Command**: `npm install`  
**Description**: Installs all the project dependencies defined in package.json into the node_modules directory.

### Execute Webpack with npx
**Command**: `npx webpack`  
**Description**: Executes webpack using npx, which runs the webpack binary installed in node_modules of the project.

### Run npm Build Script
**Command**: `npm run build`  
**Description**: Runs the build script defined in package.json, which typically invokes webpack with specific configuration.

### Remove node_modules Directory
**Command**: `rm -rf node_modules`  
**Description**: Removes the entire node_modules directory, which is a common troubleshooting step to clear out all installed dependencies.

### Clean npm Cache
**Command**: `npm cache clean --force`  
**Description**: Forces npm to clean its cache, which can resolve issues with corrupted package data.

### Re-install Dependencies
**Command**: `npm install`  
**Description**: Re-installs project dependencies, which is necessary after clearing node_modules.

### Execute Webpack after Re-installation
**Command**: `npx webpack`  
**Description**: Executes webpack again after re-installing dependencies to ensure the build process uses the latest package versions.

### Attempt to Run Build Script Again
**Command**: `npm run build`  
**Description**: Attempts to run the build script again, which should now work with the reinstalled and updated dependencies.

### Execute Gradle Wrapper
**Command**: `./gradlew build`  
**Description**: Executes the Gradle build command, which may also involve running webpack if defined in the build process.

#### The following image shows the successful Gradle build.
![gradle sucess](https://github.com/LloydCosta87/devops-23-24-JPE-1231838/assets/147655195/d56bdf6d-f10d-474a-ba2f-57d3f5f1ff9b)

#### The following image shows the successful for SpringBoot whit Gradle.
![springBoot_gradle](https://github.com/LloydCosta87/devops-23-24-JPE-1231838/assets/147655195/dd32f0a7-ba58-49c2-b45a-e5ef05ea24bf)

# 4) Network Configuration and Application Execution

## Network Interface and IP Address
**Command**: `ip addr show`  
**Description**: Displays all the network interfaces along with their IP addresses on the Ubuntu VM. It is used to identify the IP address that the VM is using on the network.

## Check Port Usage
**Command**: `sudo netstat -tulpn | grep :8080`  
**Description**: Checks which process is listening on port 8080. This is used to identify if the port is already in use and by which process. It's essential for troubleshooting port conflicts.

## Kill Process Causing Port Conflict
**Command**: `kill -9 8760/java`  
**Description**: Forcibly stops the process with the process ID (PID) 8760 that is listening on port 8080. It's crucial to stop a process that is causing a port conflict. Replace 8760 with the actual PID from your netstat command output.

## Start Spring Boot Application
**Command**: `./gradlew bootRun`  
**Description**: Executes the Gradle command to run the Spring Boot application. It compiles the application and starts it on the default port, which is typically 8080 for Spring Boot applications. The bootRun task is provided by the Spring Boot Gradle plugin.

# 5) Accessing the Application from the Host Machine

## Access from Host Machine's Web Browser
**URL**: `http://192.168.56.5:8080/`  
**Origin**: Host Machine's Web Browser  
**Description**: In the host machine (the real physical machine you are using), you access the web application running inside the virtual machine (VM) by entering this URL into your web browser.

## Start Application on Virtual Machine
**Command**: `./gradlew bootRun`  
**Origin**: Virtual Machine's Terminal  
**Description**: On the virtual machine, where Ubuntu server is installed via VirtualBox or UTM, you start the Spring Boot application by running this command.

## Importance of This Setup
**Why Is This Required?**
- **Environment Simulation**: Mirrors the production environment where servers are often separate from client devices. Ensures that the application is ready for deployment in real-world scenarios.
- **Network Connectivity Testing**: Running the server in a VM and accessing it from the host helps test network connectivity and exposes any issues with network configurations, such as firewall rules or port forwarding that might prevent access.
- **Isolation**: Operating the server within a VM isolates it from your development environment. This reduces the risk of conflicts with other services running on your host machine and provides a clear separation between development and testing environments.
- **Resource Management**: Allows for better resource management. The VM can be allocated specific resources (CPU, memory) without affecting the host system, which helps in creating a scalable and controlled testing environment.
- **Security**: Enhances security by keeping development environments contained within a VM, thus protecting the host system from potential vulnerabilities that might be exposed by the server application.
- **Deployment Practice**: Familiarizes developers with the deployment process, including how to configure and manage applications in a server environment.

This setup is a key part of software development, ensuring the application can function correctly in its intended environment and that developers can manage and troubleshoot environmental issues effectively.


