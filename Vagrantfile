# -*- mode: ruby -*-
# vi: set ft=ruby :

#provisioning script
$script = <<SCRIPT  
#download and setup apache
echo downloading and installing apache
wget http://apache.mirror.iphh.net/tomcat/tomcat-7/v7.0.56/bin/apache-tomcat-7.0.56.zip
sudo unzip -o apache-tomcat-7.0.56.zip -d /opt
sudo chown -R vagrant:vagrant /opt/apache-tomcat-7.0.56/
chmod u+x /opt/apache-tomcat-7.0.56/bin/*.sh

#configure apache
echo configuring apache
mv /opt/apache-tomcat-7.0.56/conf/tomcat-users.xml /opt/apache-tomcat-7.0.56/conf/tomcat-users.xml.org
echo "<?xml version='1.0' encoding='utf-8'?>
<tomcat-users>
	<role rolename='manager-gui' />
	<role rolename='manager-script' />
	<role rolename='manager-jmx' />
	<role rolename='manager-status' />
	<user username='vagrant' password='vagrant' roles='manager-gui,manager-script,manager-jmx,manager-status' />
</tomcat-users>
" > /opt/apache-tomcat-7.0.56/conf/tomcat-users.xml

echo apache setup complete

#compile project
echo compiling project for first time
mvn clean package -f /vagrant/pom.xml

#create link in webapps dir (to war or folder)
echo registering webapps with tomcat
ln -s /vagrant/jvm-maintenance/target/jvm-maintenance-1.5-SNAPSHOT /opt/apache-tomcat-7.0.56/webapps/jvm-maintenance
ln -s /vagrant/jaxrs-spring/target/jaxrs-spring-1.2-SNAPSHOT/ /opt/apache-tomcat-7.0.56/webapps/jaxrs-spring
ln -s /vagrant/jaxrs-plain/target/jaxrs-plain-1.2-SNAPSHOT/ /opt/apache-tomcat-7.0.56/webapps/jaxrs-plain
ln -s /vagrant/jaxws-provider/target/jaxws-provider-1.3-SNAPSHOT/ /opt/apache-tomcat-7.0.56/webapps/jaxws-provider

#run server
echo starting tomcat
/opt/apache-tomcat-7.0.56/bin/startup.sh
SCRIPT


# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = "fbrx/base"
  config.vm.hostname = "samples"
  
  config.vm.provision "shell", inline: $script, privileged: false
  
  config.vm.network "forwarded_port", guest: 8080, host: 18080
end