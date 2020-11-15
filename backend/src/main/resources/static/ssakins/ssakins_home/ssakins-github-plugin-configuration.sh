GITHUBNAME=$(sed -n 's/^ *GITID= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/name\//i\<name>'"$GITHUBNAME"'</name>' /var/jenkins_home/github-plugin-configuration.xml

sed -i '/name\//d' /var/jenkins_home/github-plugin-configuration.xml


GITHUBURL=$(sed -n 's/^ *GITHUBCONFIGURL= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/apiUrl\//i\<apiUrl>'"$GITHUBURL"'</apiUrl>' /var/jenkins_home/github-plugin-configuration.xml

sed -i '/apiUrl\//d' /var/jenkins_home/github-plugin-configuration.xml


GITHUBCREDENTIAL=$(sed -n 's/^ *GITHUBCONFIGCREDENTIAL= *=*//p' /var/jenkins_home/accountInfo.sh)
echo "import hudson.util.Secret
def password = "\"$GITHUBCREDENTIAL\""
def secret = Secret.fromString(password)
println(secret.getEncryptedValue())" > /var/jenkins_home/lab.groovy

URL=$(sed -n 's/^ *URL= *=*//p' /var/jenkins_home/accountInfo.sh)
PORT=$(sed -n 's/^ *PORT= *=*//p' /var/jenkins_home/accountInfo.sh)
ENCRYPTED_PASSWORD=$(java -jar /bin/jenkins-cli.jar -s "$URL":"$PORT"/ groovy =< /var/jenkins_home/lab.groovy)



sed -i'' -r -e '/credentialsId\//i\<credentialsId>'"$ENCRYPTED_PASSWORD"'</credentialsId>' /var/jenkins_home/github-plugin-configuration.xml

sed -i '/credentialsId\//d' /var/jenkins_home/github-plugin-configuration.xml
