sed -i'' -r -e '/jdks/i\<jdks><jdk><name>java</name><home>/usr/lib/jvm/java-11-openjdk-amd64</home></jdk></jdks>' /var/jenkins_home/config.xml

sed -i '/jdks\//d' /var/jenkins_home/config.xml
