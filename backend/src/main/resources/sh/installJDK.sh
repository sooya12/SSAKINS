#repository에서 실행

sed -i'' -r -e '/jdks/i\<jdks><jdk><name>java</name><home>/usr/lib/jvm/java-11-openjdk-amd64</home></jdk></jdks>' ./ssakins/ssakins_home/config.xml

sed -i '/jdks\//d' ./ssakins/ssakins_home/config.xml