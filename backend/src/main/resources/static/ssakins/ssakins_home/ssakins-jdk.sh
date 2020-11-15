JAVAHOME=$JAVA_HOME

sed -i'' -r -e '/jdks/i\\t<jdks>\n\t\t<jdk>\n\t\t\t<name>java</name>\n\t\t\t<home>'$JAVAHOME'</home>\n\t\t</jdk>\n\t</jdks>' /var/jenkins_home/config.xml

sed -i '/jdks\//d' /var/jenkins_home/config.xml
