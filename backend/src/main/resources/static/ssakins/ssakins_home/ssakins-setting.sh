URL=$(sed -n 's/^ *URL= *=*//p' /var/jenkins_home/accountInfo.sh)
PORT=$(sed -n 's/^ *PORT= *=*//p' /var/jenkins_home/accountInfo.sh)

wget -P /var/jenkins_home/ $URL:$PORT/jnlpJars/jenkins-cli.jar


sh /var/jenkins_home/ssakins-jdk.sh

sh /var/jenkins_home/ssakins-credentials.sh

GITKIND=$(sed -n 's/^ *GITKIND= *=*//p' /var/jenkins_home/accountInfo.sh)

if [ "$GITKIND" == "gitlab" ]; then
	sh /var/jenkins_home/ssakins-lab.sh
	echo "깃랩"

else
	sh /var/jenkins_home/ssakins-github-plugin-configuration.sh
	echo "깃헙"
fi

sh /var/jenkins_home/ssakins-ssh.sh

sh /var/jenkins_home/ssakins-job.sh

echo "SSAKINS READY!"
