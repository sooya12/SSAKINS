wget -P /bin http://k3a201.p.ssafy.io:8000/jnlpJars/jenkins-cli.jar
sleep 1

echo "NodeJS, Maven, Publish-Over-SSH install!"

java -jar /bin/jenkins-cli.jar -s http://k3a201.p.ssafy.io:8000/ install-plugin NodeJS maven-plugin publish-over-ssh -deploy -restart