
# CA3-Part2 - Virtualization with Vagrant

## Introduction
This project demonstrates the setup of a virtual environment using Vagrant to run a Spring Boot application developed in CA2-Part2.

## Prerequisites
- [Download and install Vagrant](https://www.vagrantup.com/) for your operating system.

## Update `.gitignore`
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
cp -r vagrant-multi-spring-tut-demo/path/to/files C:/Users/Utilizador/Desktop/DevOps/devops-23-24-JPE-1231838/CA3/Part2
```

## 4) Update the `Vagrantfile`
Edit the `Vagrantfile` to point to your project repository. Below is the example configuration:
```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/focal64"
  config.ssh.insert_key = false

  # This provision is common for both VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
    # ifconfig
  SHELL

  #============
  # Configurations specific to the database VM
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/focal64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"

    # We want to access H2 console from the host using port 8082
    # We want to connet to the H2 server using port 9092
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # We need to download H2
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # The following provision shell will run ALWAYS so that we can execute the H2 server process
    # This could be done in a different way, for instance, setting H2 as as service, like in the following link:
    # How to setup java as a service in ubuntu: http://www.jcgonzalez.com/ubuntu-16-java-service-wrapper-example
    #
    # To connect to H2 use: jdbc:h2:tcp://192.168.33.11:9092/./jpadb
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end

  #============
  # Configurations specific to the webserver VM
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/focal64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"

    # We set more ram memmory for this VM
    web.vm.provider "virtualbox" do |v|
      v.memory = 1024
    end

    # We want to access tomcat from the host using port 8080
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      # sudo apt-get install git -y
      # sudo apt-get install nodejs -y
      # sudo apt-get install npm -y
      # sudo ln -s /usr/bin/nodejs /usr/bin/node
      sudo apt install -y tomcat9 tomcat9-admin
      # If you want to access Tomcat admin web page do the following:
      # Edit /etc/tomcat9/tomcat-users.xml
      # uncomment tomcat-users and add manager-gui to tomcat user

      # Change the following command to clone your own repository!
      git clone https://github.com/LloydCosta87/devops-23-24-JPE-1231838.git
      cd devops-23-24-JPE-1231838/CA2/Part2/react-and-spring-data-rest-basic
      chmod u+x gradlew
      ./gradlew clean build
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
      # To deploy the war file to tomcat9 do the following command:
      # sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
    SHELL
  end
end
```

## 5) Set Up the Virtual Machines
1. **Navigate to the Project Directory**:
   ```bash
   cd C:/Users/Utilizador/Desktop/DevOps/devops-23-24-JPE-1231838/CA3/Part2
   ```
2. **Start Vagrant**:
   ```bash
   vagrant up
   ```
3. **Check Vagrant Status**:
   ```bash
   vagrant status
   ```
4. **Access Virtual Machines**:
   -DB VM:
   ```bash
    vagrant ssh db
     ```
   This acess to te VM of bd
   
   - Web VM:
     ```bash
     vagrant ssh web
     ```
  
     **Important Note:** You need to have the Virtual box open, otherwise you can't run Vagrant
     
  5. **Connect spring-boot to H2**
* On src/main/resources/application.properties add the following lines
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


     
## 7) Tag the repo
 ```bash
git tag ca3-part2
git push origin ca3-part2
```
