
# CA3-Part2 - Virtualization with Vagrant

## Introduction
This project demonstrates the setup of a virtual environment using Vagrant to run a Spring Boot application developed in CA2-Part2.

## Prerequisites
- [Download and install Vagrant](https://www.vagrantup.com/) for your operating system.

### Update `.gitignore`
Add the following lines to your `.gitignore` file:
```bash
.vagrant
*.war
```
## 1) Clone the Base Project
Clone the base Vagrant project:
```bash
git clone https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo/
```
## 3) Copy this Vagrantfile to your repository
```bash
cp -r vagrant-multi-spring-tut-demo/Vagrantfile C/Users/Utilizador/Desktop/DevOps/devops-23-24-JPE-1231838/CA3/Part2
```

## 4) Update the `Vagrantfile`
Edit the `Vagrantfile` to point to your project repository. Below is the example configuration:
```ruby
Vagrant.configure("2") do |config|
  # Specify the base VM image
  config.vm.box = "ubuntu/focal64"
  config.ssh.insert_key = false

  # Common provision script to set up both VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
  SHELL

  #============
  # Configuration for the database VM
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/focal64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"

    # Forward ports for H2 console and server access
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # Download H2 database JAR file
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # Run the H2 server, ensuring it always runs
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end

  #============
  # Configuration for the web server VM
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/focal64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"

    web.vm.provider "virtualbox" do |v|
      # Set VM resources for memory and CPUs
      v.memory = 2048
      v.cpus = 2
    end

    # Forward port for accessing the web application
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    # Provisioning script for setting up the web server
    web.vm.provision "shell", inline: <<-SHELL
      sudo apt-get update -y
      sudo apt-get install -y curl

      # Install Node.js via nodesource setup script
      curl -sL https://deb.nodesource.com/setup_20.x | sudo -E bash -
      sudo apt-get install -y nodejs

      # Clean npm cache
      sudo npm cache clean --force

      # Install Webpack globally (optional)
      sudo npm install -g webpack webpack-cli

      # Switch to the vagrant user to perform npm installs and Gradle build
      sudo -u vagrant -H bash -c "
        # Navigate to the vagrant home directory
        cd /home/vagrant

        # Clone the GitHub repository containing the application
        git clone https://github.com/LloydCosta87/devops-23-24-JPE-1231838.git

        # Change to the project directory
        cd devops-23-24-JPE-1231838/CA2/Part2/react-and-spring-data-rest-basic

        # Remove node_modules to ensure a clean state
        rm -rf node_modules

        # Install project dependencies using npm
        npm install

        # Run Webpack using npx to compile the JavaScript files
        npx webpack --config webpack.config.js

        # Ensure the Gradle wrapper is executable
        chmod +x gradlew

        # Execute the npm build script
        npm run build

        # Build the project using Gradle and provide a stack trace on error
        ./gradlew build --stacktrace

        # Run the Spring Boot application in the background and log output to a file
        nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
      "
    SHELL
  end
end
```

## 5) Set Up the Virtual Machines
1. **Navigate to the Project Directory**:
   ```bash
   cd devops-23-24-JPE-1231838/CA3/Part2
   ```
2. **Start Vagrant**:
   ```bash
   vagrant up
   ```
3. **Check Vagrant Status**:
   ```bash
   vagrant status
   ```
  
**Important Note:** You need to have the Virtual box open, otherwise you can't run Vagrant
     
##  6) **Connect spring-boot to H2**
* On
 ```
  src/main/resources/application.properties
```

add the following lines
```
server.servlet.context-path=/basic-0.0.1-SNAPSHOT
spring.data.rest.base-path=/api
spring.datasource.url=jdbc:h2:tcp://192.168.33.11:9092/./jpadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true
```

* On React app.js update the ComponentDidMount method from
```
client({method: 'GET', path: '/api/employees'}).done(response => {
```
to
```
client({method: 'GET', path: '/basic-0.0.1-SNAPSHOT/api/employees'}).done(response => {
```
* check for web
  ```
  http://localhost:8080/basic-0.0.1-SNAPSHOT
   ```
* check DB
  ```
  http://localhost:8082
   ```

  **in H2 you should have the following images:**
  
  ![Test_Connection](https://github.com/LloydCosta87/devops-23-24-JPE-1231838/assets/147655195/8e0eadb6-c33f-4067-9b7f-793477b0338e)

  In the green circle the url defined in the application.properties

  **connect**
  
![H2_expected](https://github.com/LloydCosta87/devops-23-24-JPE-1231838/assets/147655195/3a60a942-7f90-46a0-9feb-f54d06645f4f)

The Employe table should appear like the image above

**The Local Host shoul look like this**
![localHost8080](https://github.com/LloydCosta87/devops-23-24-JPE-1231838/assets/147655195/17f335cd-7216-4c63-9076-564291a0549a)


### Other commands of Vagrant

1. **`vagrant init`**:
   - Initializes a new Vagrant environment by creating a `Vagrantfile` in the current directory.
   - Usage: `vagrant init`

2. **`vagrant up`**:
   - Starts and provisions the VMs as defined in the `Vagrantfile`.
   - Usage: `vagrant up`

3. **`vagrant halt`**:
   - Stops the running VMs.
   - Usage: `vagrant halt`

4. **`vagrant suspend`**:
   - Suspends the running VMs, saving their state to be resumed later.
   - Usage: `vagrant suspend`

5. **`vagrant resume`**:
   - Resumes the VMs from a suspended state.
   - Usage: `vagrant resume`

6. **`vagrant reload`**:
   - Restarts the VMs and re-provisions them, applying any changes in the `Vagrantfile`.
   - Usage: `vagrant reload`

7. **`vagrant provision`**:
   - Runs the provisioners on the VMs without restarting them.
   - Usage: `vagrant provision`

8. **`vagrant ssh`**:
   - Logs into a running VM via SSH.
   - Usage: `vagrant ssh [vm-name]`

9. **`vagrant status`**:
   - Displays the status of the VMs defined in the `Vagrantfile`.
   - Usage: `vagrant status`

10. **`vagrant destroy`**:
    - Stops and removes the VMs defined in the `Vagrantfile`.
    - Usage: `vagrant destroy [vm-name]`

11. **`vagrant box`**:
    - Manages Vagrant box resources (list, add, remove, etc.).
    - Usage: `vagrant box <command>`

12. **`vagrant global-status`**:
    - Lists all active Vagrant environments across different directories.
    - Usage: `vagrant global-status`

     
## Alternative technological solution for the virtualization tool

I choosed 
```
VMware Workstation Player
```

- **Ease of Use**: VirtualBox is generally considered more user-friendly due to its simpler interface. VMware Workstation Player offers a polished UI but with fewer features.
- **Performance**: VMware Workstation Player is generally considered to have better performance, especially in graphics and CPU-intensive applications.
- **Networking Features**: VMware provides more advanced networking features out of the box.
- **Cross-Platform**: VirtualBox is the better choice for cross-platform compatibility due to its macOS support.

## Using VMware Workstation Player with Vagrant

To use VMware Workstation Player as a hypervisor with Vagrant, follow these steps:

### Installation

1. Download and install VMware Workstation Player.
2. Install the Vagrant VMware provider plugin with:
    ```bash
    vagrant plugin install vagrant-vmware-desktop
    ```

### Configuration in Vagrant

Update the `Vagrantfile` to specify VMware as the provider:

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "generic/ubuntu2004"  # Update to the box that supports VMware
  config.vm.provider "vmware_desktop" do |vmware|
    vmware.gui = true  # Optional: enables GUI mode for the VM
  end
end

