ITEM=$(sed -n 's/^ *ITEM= *=*//p' /var/jenkins_home/accountInfo.sh)

mkdir /var/jenkins_home/jobs/$ITEM

cp /var/jenkins_home/jobconfig.xml /var/jenkins_home/jobs/$ITEM/config.xml

mkdir /var/jenkins_home/jobs/$ITEM/builds

touch /var/jenkins_home/jobs/$ITEM/builds/legacyIds

touch /var/jenkins_home/jobs/$ITEM/builds/permalinks

echo "lastFailedBuild -1\nlastSuccessfulBuild -1" > /var/jenkins_home/jobs/$ITEM/builds/permalinks


GITREPOSITORYURL=$(sed -n 's/^ *GITREPOSITORYURL= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/projectUrl\//i\<projectUrl>'"$GITREPOSITORYURL"'</projectUrl>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/projectUrl\//d' /var/jenkins_home/jobs/$ITEM/config.xml


GITREPOSITORYGIT=$(sed -n 's/^ *GITREPOSITORYGIT= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/url\//i\<url>'"$GITREPOSITORYGIT"'</url>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/url\//d' /var/jenkins_home/jobs/$ITEM/config.xml


GITCREDENTIALID=$(sed -n 's/^ *GITCREDENTIALID= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/credentialsId\//i\<credentialsId>'"$GITCREDENTIALID"'</credentialsId>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/credentialsId\//d' /var/jenkins_home/jobs/$ITEM/config.xml


POMXMLLOCATION=$(sed -n 's/^ *POMXMLLOCATION= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/pom\//i\<pom>'"$POMXMLLOCATION"'</pom>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/pom\//d' /var/jenkins_home/jobs/$ITEM/config.xml


FRONTLOCATION=$(sed -n 's/^ *FRONTLOCATION= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/command1\//i\<command>cd '"$FRONTLOCATION"'\nnpm install\nnpm run build</command>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/command1\//d' /var/jenkins_home/jobs/$ITEM/config.xml




CONFIGNAME=$(sed -n 's/^ *CONFIGNAME= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/configName\//i\<configName>'"$CONFIGNAME"'</configName>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/configName\//d' /var/jenkins_home/jobs/$ITEM/config.xml

REMOTE=$(cat /var/jenkins_home/remoteDirectory)

FRONTSOURCEFILE=$(sed -n 's/^ *FRONTSOURCEFILE= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/frontsourceFiles\//i\<sourceFiles>'"$FRONTSOURCEFILE"'</sourceFiles>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/frontsourceFiles\//d' /var/jenkins_home/jobs/$ITEM/config.xml



FRONTREMOVEPREFIX=$(sed -n 's/^ *FRONTREMOVEPREFIX= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/frontremovePrefix\//i\<removePrefix>'"$FRONTREMOVEPREFIX"'</removePrefix>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/frontremovePrefix\//d' /var/jenkins_home/jobs/$ITEM/config.xml



FRONTEXECCOMMAND=$(sed -n 's/^ *FRONTEXECCOMMAND= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/frontexecCommand\//i\<execCommand>'"$REMOTE$FRONTEXECCOMMAND"'</execCommand>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/frontexecCommand\//d' /var/jenkins_home/jobs/$ITEM/config.xml



BACKSOURCEFILE=$(sed -n 's/^ *BACKSOURCEFILE= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/backsourceFiles\//i\<sourceFiles>'"$BACKSOURCEFILE"'</sourceFiles>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/backsourceFiles\//d' /var/jenkins_home/jobs/$ITEM/config.xml



BACKREMOVEPREFIX=$(sed -n 's/^ *BACKREMOVEPREFIX= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/backremovePrefix\//i\<removePrefix>'"$BACKREMOVEPREFIX"'</removePrefix>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/backremovePrefix\//d' /var/jenkins_home/jobs/$ITEM/config.xml



BACKEXECCOMMAND=$(sed -n 's/^ *BACKEXECCOMMAND= *=*//p' /var/jenkins_home/accountInfo.sh)

sed -i'' -r -e '/backexecCommand\//i\<execCommand>'"$REMOTE$BACKEXECCOMMAND"'</execCommand>' /var/jenkins_home/jobs/$ITEM/config.xml

sed -i '/backexecCommand\//d' /var/jenkins_home/jobs/$ITEM/config.xml
