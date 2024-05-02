
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
# See: https://manski.net/2016/09/vagrant-multi-machine-tutorial/
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/bionic64"

  # Common provision for both VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip         openjdk-11-jdk-headless
  SHELL

  # Database VM
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/bionic64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end

  # Web VM
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/bionic64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"
    web.vm.provider "virtualbox" do |v|
      v.memory = 2048
    end
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      sudo apt install -y tomcat9 tomcat9-admin

      # Configure for your Repo
      git clone https://github.com/LloydCosta87/devops-23-24-JPE-1231838.git
      cd devops-23-24-JPE-1231838/CA2/Part2
      chmod u+x gradlew
      ./gradlew clean build
      
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
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
     **Navigate to the Project Directory**
     Now run the application
     ```bash
     ./gradlew bootRun
     ```
     **Important Note:** You need to have the Virtual box open, otherwise you can't run Vagrant
     
## 7) Tag the repo
 ```bash
git tag ca3-part2
git push origin ca3-part2
```
