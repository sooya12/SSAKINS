IPADDRESS=$(sed -n 's/^ *IPADDRESS= *=*//p' /var/jenkins_home/accountInfo.sh)


sed -i'' -r -e '/hostname\//i\<hostname>'"$IPADDRESS"'</hostname>' /var/jenkins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml

sed -i '/hostname\//d' /var/jenkins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml


SERVERUSERNAME=$(sed -n 's/^ *SERVERUSERNAME= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/username\//i\<username>'"$SERVERUSERNAME"'</username>' /var/jenkins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml

sed -i '/username\//d' /var/jenkins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml


SSHNAME=$(sed -n 's/^ *SSHNAME= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/name\//i\<name>'"$SSHNAME"'</name>' /var/jenkins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml

sed -i '/name\//d' /var/jenkins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml


PEMKEY=$(sed -n 's/^ *PEMKEY= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/key\//i\<key>'"$PEMKEY"'</key>' /var/jenkins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml

sed -i '/key\//d' /var/jenkins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml


SERVERPASSWORD=$(sed -n 's/^ *SERVERPASSWORD= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/secretPassphrase\//i\<secretPassphrase>'"$SERVERPASSWORD"'</secretPassphrase>' /var/jenkins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml

sed -i '/secretPassphrase\//d' /var/jenkins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml
