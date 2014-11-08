#TODO - parameterize with download link for pache
#TODO - parameterize with git clone url
#TODO - parameterize with path to pom.xml 

#update package list
sudo apt-get update

#install tools
sudo apt-get install mc curl -y

#install java
sudo apt-get install openjdk-7-jdk -y
echo 2 | sudo update-alternatives --config java

#install maven
sudo apt-get install maven -y

#install svn and git
sudo apt-get install git subversion -y

#download and setup apache
wget http://apache.mirror.iphh.net/tomcat/tomcat-7/v7.0.56/bin/apache-tomcat-7.0.56.zip
sudo unzip apache-tomcat-7.0.56.zip -d /opt
chown -R vagrant:vagrant /opt/apache-tomcat-7.0.56/
chmod u+x /opt/apache-tomcat-7.0.56/bin/*.sh

#configure apache
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

#checkout and compile project
git clone https://github.com/fBrx/samples.git /vagrant/code
mvn clean package -f /vagrant/code/jvm-maintenance/pom.xml

#create link in webapps dir (to war or folder)
ln -s /vagrant/code/jvm-maintenance/target/jvm-maintenance-1.5-SNAPSHOT /opt/apache-tomcat-7.0.56/webapps/jvm-maintenance

#run server
/opt/apache-tomcat-7.0.56/bin/startup.sh