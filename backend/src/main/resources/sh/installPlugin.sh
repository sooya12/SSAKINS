wget -P /bin http://15.165.161.87:8000/jnlpJars/jenkins-cli.jar
sleep 1

echo "NodeJS, Maven, Publish-Over-SSH install!"

java -jar /bin/jenkins-cli.jar -s http://15.165.161.87:8000/ install-plugin NodeJS maven-plugin publish-over-ssh -deploy -restart