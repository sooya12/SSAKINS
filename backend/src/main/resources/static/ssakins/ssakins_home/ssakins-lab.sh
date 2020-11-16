GITLABCONFIGNAME=$(sed -n 's/^ *GITLABCONFIGNAME= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/name\//i\<name>'"$GITLABCONFIGNAME"'</name>' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 

sed -i '/name\//d' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 


GITLABCONFIGURL=$(sed -n 's/^ *GITLABCONFIGURL= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/url\//i\<url>'"$GITLABCONFIGURL"'</url>' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 

sed -i '/url\//d' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 


GITPASSWORD=$(sed -n 's/^ *GITPASSWORD= *=*//p' /var/jenkins_home/accountInfo.sh)
echo "import hudson.util.Secret
def password = "\"$GITPASSWORD\""
def secret = Secret.fromString(password)
println(secret.getEncryptedValue())" > /var/jenkins_home/ssakins-lab.groovy


URL=$(sed -n 's/^ *URL= *=*//p' /var/jenkins_home/accountInfo.sh)
PORT=$(sed -n 's/^ *PORT= *=*//p' /var/jenkins_home/accountInfo.sh)

ENCRYPTED_PASSWORD=$(java -jar /var/jenkins_home/jenkins-cli.jar -s "$URL":"$PORT"/ groovy =< /var/jenkins_home/ssakins-lab.groovy)

sed -i'' -r -e '/apiTokenId\//i\<apiTokenId>'"$ENCRYPTED_PASSWORD"'</apiTokenId>' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 

sed -i '/apiTokenId\//d' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 
