# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = "hashicorp/precise64"
  config.vm.provision "shell", path: "https://raw.githubusercontent.com/fBrx/samples/master/vagrant/initJavaDev.sh"
  
  config.vm.network "forwarded_port", guest: 8080, host: 18080
end