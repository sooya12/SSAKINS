GITID=$(sed -n 's/^ *GITID= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/id\//i\<id>'"$GITID"'</id>' /var/jenkins_home/credentials.xml

sed -i '/id\//d' /var/jenkins_home/credentials.xml


GITUSERNAME=$(sed -n 's/^ *GITUSERNAME= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/username\//i\<username>'"$GITUSERNAME"'</username>' /var/jenkins_home/credentials.xml

sed -i '/username\//d' /var/jenkins_home/credentials.xml


GITPASSWORD=$(sed -n 's/^ *GITPASSWORD= *=*//p' /var/jenkins_home/accountInfo.sh)

echo "import hudson.util.Secret
def password = "\"$GITPASSWORD\""
def secret = Secret.fromString(password)
println(secret.getEncryptedValue())" > /var/jenkins_home/ssakins-credential.groovy

URL=$(sed -n 's/^ *URL= *=*//p' /var/jenkins_home/accountInfo.sh)
PORT=$(sed -n 's/^ *PORT= *=*//p' /var/jenkins_home/accountInfo.sh)

ENCRYPTED_PASSWORD=$(java -jar /var/jenkins_home/jenkins-cli.jar -s "$URL":"$PORT"/ groovy =< /var/jenkins_home/ssakins-credential.groovy)

sed -i'' -r -e '/password\//i\<password>'"$ENCRYPTED_PASSWORD"'</password>' /var/jenkins_home/credentials.xml

sed -i '/password\//d' /var/jenkins_home/credentials.xml
