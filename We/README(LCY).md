# :pig_nose: 2020.10.14 ~ 2020.10.22 :joy:

## 일반 배포

* aws에 nginx 설치

```
sudo apt-get update
sudo apt-get upgrage
sudo apt-get install nginx
```



* aws에서 nginx 환경설정

```
// conf 파일 설정
cd /etc/nginx/sites-available
sudo vi default
```



* default 파일

```
server {
        listen 80 default_server;
        listen [::]:80 default_server;

        root /var/www/html/dist;

        index index.html index.htm index.nginx-debian.html;

        server_name ec2-3-17-145-7.us-east-2.compute.amazonaws.com;

        location / {
                try_files $uri $uri/ /index.html;
                proxy_redirect off;
                proxy_buffer_size       128k;
                proxy_buffers           4 256k;
                proxy_busy_buffers_size 256k;
        }

        location /blockchallen{
                proxy_pass http://localhost:8080;
                proxy_redirect off;
                charset uft-8;

                proxy_buffer_size       128k;
                proxy_buffers           4 256k;
                proxy_busy_buffers_size 256k;

                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwared-For $proxy_add_x_forwarded_for;
                proxy_set_header X-NginX-Proxy true;
        }

}

```



* nginx 

```
sudo service nginx start 			// 시작
sudo systemctl start nginx

sudo service nginx stop
sudo systemctl stop nginx			// 중지

```





#### front  배포

aws 인스턴스 상태가 안좋아서 계속 껏다켰다하고 재부팅 시켰음



* **로컬(vscode)** 

npm run build 하면 dist 폴더가 생김

scp -i C:/Users/multicampus/Documents/pem/awspwd.pem -r .\dist\ ubuntu@ec2-3-17-145-7.us-east-2.compute.amazonaws.com:/home/ubuntu

> /home/ubuntu : ssh 처음 접속했을 때 기본 폴더

복사해서 dist 폴더 생성됨 (ls 하면 확인됨)



* **git bash**

sudo cp -r dist/ /var/www/html/ : 복사

sudo service nginx restart : (오류남) 

sudo service nginx reload : (오류남) 

> 오류확인하기~
>
> sudo su
>
> #which nginx
>
> #/usr/sbin/nginx : (오류이유 알 수 있음 : 오타)
>
> ps -ef
>
> find / -name "default" : 파일 찾기



### back 배포

https://velog.io/@ojwman/linux-maven

https://bluexmas.tistory.com/832

감자가 도와줘서 해결

하여튼 중요한건 jar 파일로 빌드하는 것!

:musical_note: **ngnix 역할 **

```
reverse proxy
포트번호 숨김
```



> 로컬에서 돌리지 않았음



* **로컬(sts)** 

maven 빌드 -> target 폴더안에 .jar 

.jar 파일을 scp로 보내기



* **git bash**



### 자바 설치

```
sudo apt-get install openjdk-8-jre
sudo apt-get install openjdk-9-jdk
```



환경설정 

```
sudo vi /etc/profile
source /etc/profile
```

```
파일안 아래에 작성하기

export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
export CLASS_PATH=$JAVA_HOME/lib:$CLASS_PATH
```

> 환경설정 바로 안됐을 겨우 아무런 명령어도 먹지 않는다
>
> export PATH=/usr/bin:/bin 
>
> 로 해결



설정 reloading 

```
source /etc/profile
```



자바 설정 확인

```
java -version
```



### 메이븐 설치

설치하고 압축풀기

```
sudo wget http://mirror.navercorp.com/apache/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz

sudo tar xvf apache-maven-3.5.4-bin.tar.gz

sudo mv apche-maven-3.5.4 /opt

sudo ln -s /opt/apache-maven-3.5.4 /opt/maven
```

/opt 안에 maven 에 압축을 푼것



환경설정

```
echo "export M2_HOME=/opt/maven" | sudo tee -a /etc/profile
source /etc/profile
echo "export PATH=$PATH:$M2_HOME/bin" | sudo tee -a /etc/profile
source /etc/profile
```



메이븐 설치 확인

```
mvn -version
```



### 빌드하기

cd /home/ubuntu 에서 git clone 가능

backend 위치에서 

```
mvn package
```

하면 target 폴더안에 .jar 파일 생성

(메이븐 빌드를 해야 jar 파일 생성)



.jar 파일 빌드

```
java -jar [파일명].jar
```



> application.properties 추가해서 실행하기



![image-20201016152328486](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201016152328486.png)

인바운드 규칙 추가

http://ec2-3-17-145-7.us-east-2.compute.amazonaws.com:8080/

로 들어가짐

default 파일 :  /[api] 들어오면 proxy_pass 로 보내줌





계속 실행

```
nohup java -jar blockchallen-0.0.1-SNAPSHOT.jar &
```

> nohup 종료

```
ps -ef 

kill [PID]
```





## 도커 (백엔드 배포만)

리눅스에 설치하기

```
sudo curl -fsSL https://get.docker.com/ | sudo sh

// gpg 키 추가
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```



만든 컨테이너 실행

```
sudo docker run --rm -it ubuntu:16.04 /bin/bash
```

> 컨테이너 만들 필요 없음
>
> 도커 엔진 설치할 필요없음



도커 설치 확인

```
sudo docker run hello-world
```



/home/ubuntu 에 도커 이미지 만들 폴더 만들기

```
mkdir dockerback
```

 이 폴더 안에 .jar 파일, Dockerfile( 오타 주의 ) 저장하기



Dockerfile

```
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar 		// target/jar 넣기 (경로지정)
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```



.jar 파일은 mvn build 된거 복사하기



> 로컬에서 빌드 했을 경우 복사하기

```
scp -i [pem 경로] -r [프로젝트 경로]/backend/target/*.jar ubuntu@[퍼블릭DNS]:~/dockerback
```



도커 이미지 생성 및 실행하기

```
~/dockerback$ sudo docker build -t dockerback .		// 이미지 빌드

sudo docker images		// 이미지 확인

sudo docker run -d -p 8081:8080 dockerback // 일반배포 백그라운드 8080 실행중 -> 8081로 실행

sudo docker ps -a 		// 도커 이름 확인
```





## 젠킨스

* 자바 설치하기

```
which java // 환경변수 설정 위치
```



* 젠킨스 설치하기

:ballot_box_with_check:참고 :  https://pkg.jenkins.io/debian-stable/

```
wget -qO - https://pkg.jenkins.io/debian-stable/jenkins.io.key | apt-key add -
```

>// 설치를 위해 sources.list에 접근
>$ cd /etc/apt
>$ sudo vi sources.list
>// sources.list에 추가
>deb https://pkg.jenkins.io/debian-stable binary/ 





* 젠킨스 포트 설정

```
vi /etc/default/jenkins 		// HTTP PORT 변경
```



* 젠킨스 키넣기 ( 비번 )

젠킨스 아이디/비번

젠킨스 user id : admin



* 젠킨스에 플러그인 추가

gradle, git, github, gitlab, nodejs, ssh, public ssh ...

github integration



* git - 젠킨스 연동

  * webhook : git setting 

  jenkins : add credential > secret text.  polling 체크...

  * Publish over SSH 설정 : pem 키 넣기, hostname 등 (비번추가해서 넣어야됨)

  * item 만들기 > manage credential 설정 (global) > github app 말고 username with password 설정하기

  * 젠킨스 구성 설정 > 소스코드 관리 git 설정

:ballot_box_with_check: 참고 : https://lindarex.github.io/jenkins/jenkins-github-webhook-setting/

완료하면 ssh에서 젠킨스 폴더에서 파일들을 확인할 수 있음



* 빌드하기

jenkins > global tool configration 

nodejs + maven 설치

빌드환경 > script 작성 가능 (provide node 체크)

```
// execute shell

cd frondend
npm install
npm run build

cd [main/resources .. ]
echo .. 		// application.properties 생성
```

```
// maven targets
		
mvn package 		// goals
```



global tool config > JDK 설정 : JAVA_HOME 에러

결과 : dist폴더, target 생성



**---CI완료---**



* 빌드 후 조치 설정

deploy 폴더 설정 < dist 폴더, .jar 파일 넣기 : transfer set 설정하기

ci 성공임~



* nginx 설치



* 스크립트 파일 만들기

front.sh

```
cp -r ~/deploy/dist/ /var/www/html/
service nginx restart
```



back.sh

```
CURRENT_PID=$(ps -ef | grep java | grep block | awk '{print $2}')
        if [ $CURRENT_PID ]; then
                kill -9 $CURRENT_PID
                echo "현재 구동중인 서버 pid : $CURRENT_PID -> 종료"
                sleep 10
        else
                echo "현재 구동중인 서버가 없습니다."
        fi
        echo "새 백엔드 서버 구동"
        nohup java -jar ~/deploy/*.jar &
```

이 파일을 [빌드 후 조치] 에서 exec command 로 실행

```
sh ~/deploy/front.sh
sh ~/deploy/back.sh 
```



> * /etc/nginx/sites-available/default 파일 설정
>
> root /var/www/html/dist
>
> location /{} 설정
>
> ```
> ln -s /etc/nginx/sites-available/default /etc/nginx/sites-enabled/default
> ```



**---중단배포---**





## 무중단 배포 (도커사용)

* Dockerfile 만들기

```
FROM openjdk:11
ARG JAR_FILE=./*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dserver.port=8080","app.jar"]
```





* 도커 

```
docker build -t [이미지이름] .
docker run --name "[이미지이름]" -p 8080:8080 -d [이미지이름]
docker ps -a
```

dockerback.sh :  위 코드 파일화

```
DOCKER_BE=$(docker ps -a |grep be)

        if [ "$DOCKER_BE" ]; then
                echo "docker exit"
                docker stop be
                docker rm be
                docker rmi be

        fi
echo "docker build and run"
docker build -t be .
docker run --name "be" -p 8080:8080 -d be
```





* 포트가 다른 두개의 파일 생성 

dockerA.sh 

```
docker build -t bea .
docker run --name "bea" -p 8081:8080 -d bea
```

dockerB.sh

```
docker build -t beb .
docker run --name "beb" -p 8082:8080 -d beb
```





* 두 개의 파일을 조건에 맞도록 새 파일 생성

mudocker.sh

```
BEA_IS_RUNNING=$(docker ps -a |grep bea)

        if [ "$BEA_IS_RUNNING" ]; then
                echo "8082 포트로 실행합니다."
                sh dockerB.sh

                sleep 10
                echo "8081 포트를 종료 시킵니다."
                docker stop bea
                docker rm bea
                docker rmi bea
        else
                echo "8081 포트로 실행합니다."
                sh dockerA.sh

                sleep 10
                if [ "$(docker ps -a |grep beb)" ]; then
                        echo "8082 포트를 종료 시킵니다."
                        docker stop beb
                        docker rm beb
                        docker rmi beb
                fi


        fi
```





* /etc/nginx/sites-available/default 설정

 포트 포워딩

upstream 

새로운 server 등록 > 프록시 설정

nginx... 음... ㅇㅋ...



-----------------



gitlab -> github : https://lazyren.github.io/devlog/gitlab-to-github-repo-clone.html

webhook 환경구축 : https://ict-nroo.tistory.com/37

nginx 공식 : http://nginx.org/en/docs/http/ngx_http_upstream_module.html







# 2020년 10월 26일

/var/lib/jenkins/plugins

config.xml

jobs/test



$ java -jar ~/bin/jenkins-cli.jar -s http://localhost:8080/ install-plugin NodeJS -deploy -restart







# 2020년 10월 27일

* spring boot에서 쉘 스크립트 코드를 실행하기 위함

  1. ssh 로 접속

     ```java
     private static String keyname="C:/Users/multicampus/Documents/pem/awspwd.pem";
     private static String publicDNS = "ec2-3-17-145-7.us-east-2.compute.amazonaws.com";
     
     JSch jsch = new JSch();
     String user = "ubuntu";	 	// 마음대로 설정가능
     String host = publicDNS;	
     int port =22;
     String privateKey = keyname;
     			
     jsch.addIdentity(privateKey);
     			
     Session session = jsch.getSession(user, host, port);	// 세선 설정
     session.setConfig("StrictHostKeyChecking","no");
     session.setConfig("GSSAPIAuthentication","no");
     session.setServerAliveInterval(120 * 1000);
     session.setServerAliveCountMax(1000);
     session.setConfig("TCPKeepAlive","yes");
     
     session.connect();		// 세션 연결
     
     ```

     

  2. 쉘 스크립트 코드 사용

     ```java
     Channel channel = session.openChannel("exec");
     ChannelExec channelExec = (ChannelExec) channel;
     
     channelExec.setCommand("sh /home/ubuntu/test.sh");		// 리눅스 명령어 
     channelExec.connect();									// 명령어 연결
     ```

     

> **삽질 내용**
>
> * ssh로 접속하지 않으면 window 기 때문에 리눅스 명령어 안먹힘. 근데 계속 삽질함.
>
> cmd 명령어 성공하고 정신차림.
>
> * ssh로 접속하려고 처음 시도했을 때, publicDNS 를 잘 못 적어둠. 무슨 정신인지 나도 모름. 내 AWS서버 주소로 고치니깐 접속됨





# 2020년 10월 28일(수)



## mongoDB 학습

* 도커를 통해서 디비에 접속하기

```
// 몽고 디비 시작
$ sudo docker start mongodb

// 몽고 디비 실행
$ sudo docker exec -u root -it mongodb /bin/bash

// 몽고 디비 접속
root# mongo
```



* 몽고 디비 구조

**database** 동일 | table => **collection** | recodes, row => **document**



* database

```sql
// database 조회
> show dbs

// database 접속 및 생성
// (단, 생성하고 바로 조회는 불가 -> 안에 내용을 추가하면 조회 가능)
> use ssakins

// 현재 사용중인 database
> db
```



* collection 

```sql
// collection(table) 조회
> show collections

// 생성
> db.createCollection("컬렉션이름")

// 삭제
> db.컬렉션이름.drop()

```



* document

```sql
// 삽입
> db.컬렉션이름.insert({"key":"value"})

// 삭제
> db.컬렉션이름.remove({"key":"value"},true)

// 수정
> db.컬렉션이름.update({"key1","value1"},{$set:{"key2":"value2"}})

// 조회
> db.컬렉션이름.find()
> db.컬렉션이름.find().pretty()		// 예쁘게 조회


// 부분 조회 1
// key1이 value인 document 찾기
> db.컬렉션이름.find({"key1":"value"})


// 부분 조회 2
// key1이 value인 key2 값 찾기 && _id 는 출력하지 않음 
> db.컬렉션이름.find({"key1":"value"},{"_id":false,"key2":true})

```



> **비교**
>
> $eq : 동일한 값
>
> $ne : 다른 값
>
> $gt : 큰 값
>
> $gre : 크거나 같은 값
>
> $lt : 작은 갑
>
> $lte : 작거나 같은 값
>
> $in : 배열에 속하는 어떤 값
>
> $nin : 배열에 속하지 않는 어떤 값
>
> 
>
> **논리**
>
> $or 	$and 	$not 	$nor
>
> 
>
> **요소**
>
> $exists
>
> $type
>
> 
>
> **평가**
>
> $mod
>
> $regex		// 정규표현식과 일치하는 document 선택
>
> $text			// 텍스트 검색
>
> $where		// javascript
>
> 
>
> **배열**
>
> $all 				// 모든 요소 포함하는 배열
>
> $elemMatch // 지정된 조건과 모두 일치
>
> $size	

```sql
// 예시1
> db.컬렉션이름.find({"score":{$gt:45,$lte:70}}).pretty()

// 예시2
> db.컬렉션이름.find({"grade":{$nin:["A","B","C","D"]}}).pretty()

// 예시3
> db.컬렉션이름.find({$and:[{"sex":"male","score":{$lte:50}}]}).pretty()
```



* 배열 array

```sql
// 추가 $push
// key1, value1인 배열에 key2:value2 추가
> db.컬렉션이름.update({"key1":"value1"},{$push:{"key2":"value2"}})

// 삭제 $pull
```

> **제한자**
>
> $set		$unset
>
> $nc		 $inc
>
> $push	$pull
>
> $addToSet
>
> $each
>
> $pop



:white_check_mark: 참고 : https://doorbw.tistory.com/19





* 날짜

```sql
// 현재 시간 추가
> db.컬렉션이름.insert({"date":ISODate()})
```

```
// 한국 시간 매핑 방법 1
function getCurrentDate(){
	var date = new Date();
	var year = date.getFullYear();
	var month = data.getDate();
	var today = date.getHours();
	var minutes = date.getMinutes();
	var seconds = date.getSeconds();
	var milliseconds = date.getMilliseconds();
	
	return new Date(Date.UTC(year, month, today, hours, minutes, seconds, milliseconds));
}
```



:star: **몽고디비**

> root/ssakins



# 2020년 10월 30일

* 몽고디비 특징





# 2020년 11월 2일

* sed 명령어

**<jdks/> 를 <jdks></jdks>로 치환하기 실패~**

```shell
// jdk가 속한 한 줄 출력
sed -n '/jdk/p' config.xml

// jdk가 속한 줄 삭제
sed -i '/jdk/d' config.xml

// <viewsTabBar> 위에 삽입
sed -i'' -r -e '/viewsTabBar/i\<jdks/>' config.xml

```



**기존에 있는 것 기준으로 넣고 -> 기존 것 삭제**

```shell
// 기존에 들어있는 것
<jdks/> 

// <jdks/> 있는 곳 위에 <jdks><jdk><name></name></jdk></jdks> 삽입
sed -i'' -r -e '/jdks/i\<jdks><jdk><name>java</name><home>/usr/lib/jvm/java-11-openjdk-amd64</home></jdk></jdks>' config.xml

// 기존에 있던 <jdks/> 얘만 삭제
sed -i '/jdks\//d' config.xml
```



* 내 서버 위에 도커사용하기

```shell
// 도커 컨테이너 확인하기
docker ps -a

// 도커 이미지 다 삭제하기
docker rm $(docker ps -a -q)

// 언집 가능하게 설치
sudo apt install unzip

// 집 파일 설치하고 풀기
wget http://49.50.161.106:8080/zip -O ssakins.zip && unzip -d chaengRepository ssakins.zip && rm ssakins.zip


// sh install.sh 파일 수정하기 포트번호 && 컨테이너 이름


// 집파일로 받은 install.sh 실행 (ssakins 도커이미지 다운)
sh install.sh

// config.xml 같은 파일 위치
cd /home/ubuntu/repo/ssakins/ssakins_home

// 도커 다시시작
docker restart ssakins1

// root 에 접근
sudo docker exec -u root -it ssakins2 /bin/bash 

// 도커 확인
sudo docker ps -a

// 도커 죽이기
sudo docker kill [도커 이미지 이름]
sudo docker rm [도커 이미지 이름]


```





```shell
// install.sh
echo "["`date`"] start to install SSAKINS."
sleep 1

echo "["`date`"] pull SSAKINS image from docker-hub... "
sudo docker pull phm0127/ssakins
sleep 1

echo -n "["`date`"] Set environments for SSAKINS ... "

sp='/-\|'
printf ' '
for n in `seq 0 10` ; do
    printf '\b%.1s' "$sp"
    sleep 0.5
    sp=${sp#?}${sp%???}
done
echo ""
SSAKINS_HOME=`pwd`
sudo chown 1000 $SSAKINS_HOME/ssakins_home
echo "["`date`"] set SSAKINS_HOME directory owned by root."
sleep 1

sudo docker run --name "ssakins1" -u 'root' -v $SSAKINS_HOME/ssakins_home/:/var/jenkins_home/ -p 8181:8080 -d phm0127/ssakins
echo -n "["`date`"] run SSAKINS on docker... "

printf ' '
for n in `seq 0 10` ; do
    printf '\b%.1s' "$sp"
    sleep 0.5
    sp=${sp#?}${sp%???}
done
echo ""


echo "  #####    #####     ###    ##  ##    ####    ##   ##   #####
 ##   ##  ##   ##   ## ##   ##  ##     ##     ###  ##  ##   ##
 ##       ##       ##   ##  ## ##      ##     #### ##  ##
  #####    #####   #######  ####       ##     ## ####   #####
      ##       ##  ##   ##  ## ##      ##     ##  ###       ##
 ##   ##  ##   ##  ##   ##  ##  ###    ##     ##   ##  ##   ##
  #####    #####   ##   ##  ##   ##   ####    ##   ##   ##### "
sleep 1
echo "["`date`"] success install SSAKINS"

```



# 2020년 11월 03일

* git 설정

root@9fbe0e0dbea3:/var/jenkins_home# cat hudson.plugins.git.GitTool.xml
<?xml version='1.1' encoding='UTF-8'?>
<hudson.plugins.git.GitTool_-DescriptorImpl plugin="git-client@3.5.1">
  <installations class="hudson.plugins.git.GitTool-array">
    <hudson.plugins.git.GitTool>
      <name>git</name>
      <home>/usr/bin/git</home>
      <properties/>
    </hudson.plugins.git.GitTool>
  </installations>



![image-20201103113617301](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201103113617301.png)





# 2020년 11월 04일

:white_check_mark: **ssakins_home -> docker**

ssakins 2 <- Repository2

ssakins_home 폴더에 maven 추가, config.xml 수정

install.sh 실행해서 jenkins_home 에 반영되는지 확인

-----------------

:white_check_mark: **jobs 관련 내용 생성되는지 확인**

item 생성

* item/builds 
* item/config.xml : 기본이 아니어도 반영됨
* item/builds/legacyIds : 빈파일
* item/builds/permalinks 

permalinks 내용

lastFailedBuild -1
lastSuccessfulBuild -1

<< nodejs, pubilsh ssh 플러그인 설치 

--------------------------------

:white_check_mark: **비밀번호 암호 관련**

현수가 groovy로 해결함

java -jar /bin/jenkins-cli.jar -s http://k3a201.p.ssafy.io:8888/ groovy = < /test.groovy

```groovy
// test.groovy

import hudson.util.Secret

def secret = Secret.fromString("your password")
println(secret.getEncryptedValue())
```





# 2020년 11월 06일

* ssh.sh

```shell
IPADDRESS=$(sed -n 's/^ *IPADDRESS *=*//p' ./Data)


sed -i'' -r -e '/hostname\//i\<hostname>'"$IPADDRESS"'</hostname>' ./ssakins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml

sed -i '/hostname\//d' ./ssakins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml


SERVERUSERNAME=$(sed -n 's/^ *SERVERUSERNAME *=*//p' ./Data)

sed -i'' -r -e '/username\//i\<username>'"$SERVERUSERNAME"'</username>' ./ssakins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml

sed -i '/username\//d' ./ssakins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml


SSHSSH=$(sed -n 's/^ *SSHSSH *=*//p' ./Data)

sed -i'' -r -e '/name\//i\<name>'"$SSHSSH"'</name>' ./ssakins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml

sed -i '/name\//d' ./ssakins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml


PEMKEY=$(sed -n 's/^ *PEMKEY *=*//p' ./Data)

sed -i'' -r -e '/key\//i\<key>'"$PEMKEY"'</key>' ./ssakins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml

sed -i '/key\//d' ./ssakins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml


SERVERPASSWORD=$(sed -n 's/^ *SERVERPASSWORD *=*//p' ./Data)

sed -i'' -r -e '/secretPassphrase\//i\<secretPassphrase>'"$SERVERPASSWORD"'</secretPassphrase>' ./ssakins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml

sed -i '/secretPassphrase\//d' ./ssakins_home/jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin.xml

```



# 2020년 11월 09일

* jenkins.plugins.publish__over__ssh.BapSshPublisherPlugin 기본 파일

```shell
<?xml version='1.1' encoding='UTF-8'?>
<jenkins.plugins.publish__over__ssh.BapSshPublisherPlugin_-Descriptor plugin="publish-over-ssh@1.20.1">
    <hostConfigurations>
        <jenkins.plugins.publish__over__ssh.BapSshHostConfiguration>
            <name/>
            <hostname/>
            <username/>
            <secretPassword></secretPassword>
            <remoteRootDir></remoteRootDir>
            <port>22</port>
            <commonConfig class="jenkins.plugins.publish_over_ssh.BapSshCommonConfiguration">
                <secretPassphrase></secretPassphrase>
                <key/>
                <keyPath></keyPath>
                <disableAllExec>false</disableAllExec>
            </commonConfig>
            <timeout>300000</timeout>
            <overrideKey>true</overrideKey>
            <disableExec>false</disableExec>
            <keyInfo>
                <secretPassphrase/>
                <key></key>
                <keyPath></keyPath>
            </keyInfo>
            <jumpHost></jumpHost>
            <proxyType></proxyType>
            <proxyHost></proxyHost>
            <proxyPort>0</proxyPort>
            <proxyUser></proxyUser>
            <proxyPassword></proxyPassword>
        </jenkins.plugins.publish__over__ssh.BapSshHostConfiguration>
    </hostConfigurations>
    <commonConfig reference="../hostConfigurations/jenkins.plugins.publish__over__ssh.BapSshHostConfiguration/commonConfig"/>
    <defaults class="jenkins.plugins.publish_over_ssh.options.SshPluginDefaults"/>
</jenkins.plugins.publish__over__ssh.BapSshPublisherPlugin_-Descriptor>
```



* 임시 Data

```shell
[ssh]
IPADDRESS=ip
SERVERUSERNAME=server
SSHSSH=ssh
PEMKEY=pemkey
SERVERPASSWORD=serverpassword
--------------------------------
[job]
ITEM=concon
GITREPOSITORYURL=gitrepourl
GITREPOSITORYGIT=.git
CREDENTIALID=creid
POMXMLLOCATION=/pom.xml
FRONTLOCATION=/frontend
APPLICATIONPROPERTIES=application.properties
JOBSSH=ssh
FRONTPREFIX=fprefix
FRONTSH=frontsh
BACKLOCATION=/backend
BACKSH=backsh
--------------------------------
[lab]
GITLABNAME=gitlabname
GITLABURL=url
GITLABCREDENTIAL=credential
--------------------------------
```



* jobs.item.config.xml 기본 파일

```shell
<?xml version='1.1' encoding='UTF-8'?>
<project>
    <actions/>
    <description></description>
    <keepDependencies>false</keepDependencies>
    <properties>
        <com.coravy.hudson.plugins.github.GithubProjectProperty plugin="github@1.31.0">
            <projectUrl/>
            <displayName></displayName>
        </com.coravy.hudson.plugins.github.GithubProjectProperty>
        <com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty plugin="gitlab-plugin@1.5.13">
            <gitLabConnection></gitLabConnection>
        </com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty>
    </properties>
    <scm class="hudson.plugins.git.GitSCM" plugin="git@4.4.4">
        <configVersion>2</configVersion>
        <userRemoteConfigs>
            <hudson.plugins.git.UserRemoteConfig>
                <url/>
                <credentialsId/>
            </hudson.plugins.git.UserRemoteConfig>
        </userRemoteConfigs>
        <branches>
            <hudson.plugins.git.BranchSpec>
                <name>*/master</name>
            </hudson.plugins.git.BranchSpec>
        </branches>
        <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
        <submoduleCfg class="list"/>
        <extensions/>
    </scm>
    <canRoam>true</canRoam>
    <disabled>false</disabled>
    <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
    <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
    <triggers>
        <com.dabsquared.gitlabjenkins.GitLabPushTrigger plugin="gitlab-plugin@1.5.13">
            <spec></spec>
            <triggerOnPush>true</triggerOnPush>
            <triggerOnMergeRequest>true</triggerOnMergeRequest>
            <triggerOnPipelineEvent>false</triggerOnPipelineEvent>
            <triggerOnAcceptedMergeRequest>false</triggerOnAcceptedMergeRequest>
            <triggerOnClosedMergeRequest>false</triggerOnClosedMergeRequest>
            <triggerOnApprovedMergeRequest>true</triggerOnApprovedMergeRequest>
            <triggerOpenMergeRequestOnPush>never</triggerOpenMergeRequestOnPush>
            <triggerOnNoteRequest>true</triggerOnNoteRequest>
            <noteRegex>Jenkins please retry a build</noteRegex>
            <ciSkip>true</ciSkip>
            <skipWorkInProgressMergeRequest>true</skipWorkInProgressMergeRequest>
            <setBuildDescription>true</setBuildDescription>
            <branchFilterType>All</branchFilterType>
            <includeBranchesSpec></includeBranchesSpec>
            <excludeBranchesSpec></excludeBranchesSpec>
            <sourceBranchRegex></sourceBranchRegex>
            <targetBranchRegex></targetBranchRegex>
            <secretToken></secretToken>
            <pendingBuildName></pendingBuildName>
            <cancelPendingBuildsOnUpdate>false</cancelPendingBuildsOnUpdate>
        </com.dabsquared.gitlabjenkins.GitLabPushTrigger>
        <com.cloudbees.jenkins.GitHubPushTrigger plugin="github@1.31.0">
            <spec></spec>
        </com.cloudbees.jenkins.GitHubPushTrigger>
    </triggers>
    <concurrentBuild>false</concurrentBuild>
    <builders>
        <hudson.tasks.Maven>
            <targets>package</targets>
            <mavenName>maven</mavenName>
            <pom/>
            <usePrivateRepository>false</usePrivateRepository>
            <settings class="jenkins.mvn.DefaultSettingsProvider"/>
            <globalSettings class="jenkins.mvn.DefaultGlobalSettingsProvider"/>
            <injectBuildVariables>false</injectBuildVariables>
        </hudson.tasks.Maven>
        <hudson.tasks.Shell>
            <command1/>
            <configuredLocalRules/>
        </hudson.tasks.Shell>
        <hudson.tasks.Shell>
            <command2/>
            <configuredLocalRules/>
        </hudson.tasks.Shell>
    </builders>
    <publishers>
        <jenkins.plugins.publish__over__ssh.BapSshPublisherPlugin plugin="publish-over-ssh@1.20.1">
            <consolePrefix>SSH: </consolePrefix>
            <delegate plugin="publish-over@0.22">
                <publishers>
                    <jenkins.plugins.publish__over__ssh.BapSshPublisher plugin="publish-over-ssh@1.20.1">
                        <configName/>
                        <verbose>false</verbose>
                        <transfers>
                            <jenkins.plugins.publish__over__ssh.BapSshTransfer>
                                <remoteDirectory>deploy</remoteDirectory>
                                <frontsourceFiles/>
                                <excludes></excludes>
                                <frontremovePrefix/>
                                <remoteDirectorySDF>false</remoteDirectorySDF>
                                <flatten>false</flatten>
                                <cleanRemote>false</cleanRemote>
                                <noDefaultExcludes>false</noDefaultExcludes>
                                <makeEmptyDirs>false</makeEmptyDirs>
                                <patternSeparator>[, ]+</patternSeparator>
                                <frontexecCommand/>
                                <execTimeout>120000</execTimeout>
                                <usePty>false</usePty>
                                <useAgentForwarding>false</useAgentForwarding>
                            </jenkins.plugins.publish__over__ssh.BapSshTransfer>
                            <jenkins.plugins.publish__over__ssh.BapSshTransfer>
                                <remoteDirectory>deploy</remoteDirectory>
                                <backsourceFiles/>
                                <excludes></excludes>
                                <backremovePrefix/>
                                <remoteDirectorySDF>false</remoteDirectorySDF>
                                <flatten>false</flatten>
                                <cleanRemote>false</cleanRemote>
                                <noDefaultExcludes>false</noDefaultExcludes>
                                <makeEmptyDirs>false</makeEmptyDirs>
                                <patternSeparator>[, ]+</patternSeparator>
                                <backexecCommand/>
                                <execTimeout>120000</execTimeout>
                                <usePty>false</usePty>
                                <useAgentForwarding>false</useAgentForwarding>
                            </jenkins.plugins.publish__over__ssh.BapSshTransfer>
                        </transfers>
                        <useWorkspaceInPromotion>false</useWorkspaceInPromotion>
                        <usePromotionTimestamp>false</usePromotionTimestamp>
                    </jenkins.plugins.publish__over__ssh.BapSshPublisher>
                </publishers>
                <continueOnError>false</continueOnError>
                <failOnError>false</failOnError>
                <alwaysPublishFromMaster>false</alwaysPublishFromMaster>
                <hostConfigurationAccess class="jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin" reference="../.."/>
            </delegate>
        </jenkins.plugins.publish__over__ssh.BapSshPublisherPlugin>
    </publishers>
    <buildWrappers>
        <jenkins.plugins.nodejs.NodeJSBuildWrapper plugin="nodejs@1.3.9">
            <nodeJSInstallationName>nodejs</nodeJSInstallationName>
            <cacheLocationStrategy class="jenkins.plugins.nodejs.cache.DefaultCacheLocationLocator"/>
        </jenkins.plugins.nodejs.NodeJSBuildWrapper>
    </buildWrappers>
</project>
```



* job.sh

```shell
ITEM=$(sed -n 's/^ *ITEM *=*//p' ./Data)

mkdir ./ssakins_home/jobs/$ITEM

cp config.xml ./ssakins_home/jobs/$ITEM/

mkdir ./ssakins_home/jobs/$ITEM/builds

touch ./ssakins_home/jobs/$ITEM/builds/legacyIds

touch ./ssakins_home/jobs/$ITEM/builds/permalinks

echo "lastFailedBuild -1\nlastSuccessfulBuild -1" > ./ssakins_home/jobs/$ITEM/builds/permalinks


GITREPOSITORYURL=$(sed -n 's/^ *GITREPOSITORYURL *=*//p' ./Data)

sed -i'' -r -e '/projectUrl\//i\<projectUrl>'"$GITREPOSITORYURL"'</projectUrl>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/projectUrl\//d' ./ssakins_home/jobs/$ITEM/config.xml


GITREPOSITORYGIT=$(sed -n 's/^ *GITREPOSITORYGIT *=*//p' ./Data)

sed -i'' -r -e '/url\//i\<url>'"$GITREPOSITORYGIT"'</url>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/url\//d' ./ssakins_home/jobs/$ITEM/config.xml


CREDENTIALID=$(sed -n 's/^ *CREDENTIALID *=*//p' ./Data)

sed -i'' -r -e '/credentialsId\//i\<credentialsId>'"$CREDENTIALID"'</credentialsId>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/credentialsId\//d' ./ssakins_home/jobs/$ITEM/config.xml


POMXMLLOCATION=$(sed -n 's/^ *POMXMLLOCATION *=*//p' ./Data)

sed -i'' -r -e '/pom\//i\<pom>'"$POMXMLLOCATION"'</pom>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/pom\//d' ./ssakins_home/jobs/$ITEM/config.xml


FRONTLOCATION=$(sed -n 's/^ *FRONTLOCATION *=*//p' ./Data)

sed -i'' -r -e '/command1\//i\<command>cd '"$FRONTLOCATION"'\nnpm install\nnpm run build</command>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/command1\//d' ./ssakins_home/jobs/$ITEM/config.xml


APPLICATIONPROPERTIES=$(sed -n 's/^ *APPLICATIONPROPERTIES *=*//p' ./Data)

sed -i'' -r -e '/command2\//i\<command>'"$APPLICATIONPROPERTIES"'</command>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/command2\//d' ./ssakins_home/jobs/$ITEM/config.xml



JOBSSH=$(sed -n 's/^ *JOBSSH *=*//p' ./Data)

sed -i'' -r -e '/configName\//i\<configName>'"$JOBSSH"'</configName>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/configName\//d' ./ssakins_home/jobs/$ITEM/config.xml



sed -i'' -r -e '/frontsourceFiles\//i\<sourceFiles>'"$FRONTLOCATION"'/dist</sourceFiles>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/frontsourceFiles\//d' ./ssakins_home/jobs/$ITEM/config.xml



FRONTPREFIX=$(sed -n 's/^ *FRONTPREFIX *=*//p' ./Data)

sed -i'' -r -e '/frontremovePrefix\//i\<removePrefix>'"$FRONTPREFIX"'</removePrefix>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/frontremovePrefix\//d' ./ssakins_home/jobs/$ITEM/config.xml



FRONTSH=$(sed -n 's/^ *FRONTSH *=*//p' ./Data)

sed -i'' -r -e '/frontexecCommand\//i\<execCommand>sh ~/deploy/'"$FRONTSH"'</execCommand>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/frontexecCommand\//d' ./ssakins_home/jobs/$ITEM/config.xml



BACKLOCATION=$(sed -n 's/^ *BACKLOCATION *=*//p' ./Data)

sed -i'' -r -e '/backsourceFiles\//i\<sourceFiles>'"$BACKLOCATION"'/target/*.jar</sourceFiles>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/backsourceFiles\//d' ./ssakins_home/jobs/$ITEM/config.xml



sed -i'' -r -e '/backremovePrefix\//i\<removePrefix>'"$BACKLOCATION"'/target</removePrefix>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/backremovePrefix\//d' ./ssakins_home/jobs/$ITEM/config.xml



BACKSH=$(sed -n 's/^ *BACKSH *=*//p' ./Data)

sed -i'' -r -e '/backexecCommand\//i\<execCommand>sh ~/deploy/'"$BACKSH"'</execCommand>' ./ssakins_home/jobs/$ITEM/config.xml

sed -i '/backexecCommand\//d' ./ssakins_home/jobs/$ITEM/config.xml


```



* com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 기본 파일

```shell
<?xml version='1.1' encoding='UTF-8'?>
<com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig plugin="gitlab-plugin@1.5.13">
  <useAuthenticatedEndpoint>true</useAuthenticatedEndpoint>
  <connections>
    <com.dabsquared.gitlabjenkins.connection.GitLabConnection>
      <name/>
      <url/>
      <apiTokenId/>
      <clientBuilder class="com.dabsquared.gitlabjenkins.gitlab.api.impl.AutodetectGitLabClientBuilder">
        <id>autodetect</id>
        <ordinal>0</ordinal>
      </clientBuilder>
      <ignoreCertificateErrors>false</ignoreCertificateErrors>
      <connectionTimeout>10</connectionTimeout>
      <readTimeout>10</readTimeout>
    </com.dabsquared.gitlabjenkins.connection.GitLabConnection>
  </connections>
</com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig>

```



* lab.sh

```shell
GITLABNAME=$(sed -n 's/^ *GITLABNAME *=*//p' ./Data)

sed -i'' -r -e '/name\//i\<name>'"$GITLABNAME"'</name>' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 

sed -i '/name\//d' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 



GITLABURL=$(sed -n 's/^ *GITLABURL *=*//p' ./Data)

sed -i'' -r -e '/url\//i\<url>'"$GITLABURL"'</url>' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 

sed -i '/url\//d' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 




GITLABCREDENTIAL=$(sed -n 's/^ *GITLABCREDENTIAL *=*//p' /var/jenkins_home/Data)
echo "import hudson.util.Secret
def password = "\"$GITLABCREDENTIAL\""
def secret = Secret.fromString(password)
println(secret.getEncryptedValue())" > /var/jenkins_home/lab.groovy

ENCRYPTED_PASSWORD=$(java -jar /bin/jenkins-cli.jar -s http://k3a201.p.ssafy.io:8282/ groovy =< /var/jenkins_home/lab.groovy)
echo $ENCRYPTED_PASSWORD


sed -i'' -r -e '/apiTokenId\//i\<apiTokenId>'"$ENCRYPTED_PASSWORD"'</apiTokenId>' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 

sed -i '/apiTokenId\//d' /var/jenkins_home/com.dabsquared.gitlabjenkins.connection.GitLabConnectionConfig.xml 

```

주소 커스텀 해야됨



* lab.groovy 만들어진 모습

```groovy
import hudson.util.Secret
def password = "credential"
def secret = Secret.fromString(password)
println(secret.getEncryptedValue())
```





* 그루비 사용하기 위해서 ssakins_home 위치에서 설치

```shell
wget -P /bin http://k3a201.p.ssafy.io:8282/jnlpJars/jenkins-cli.jar
```



* ssakins_home/gitcredential.groovy

```shell
import hudson.util.Secret
def secret = Secret.fromString("1234")
println(secret.getEncryptedValue())

```

여기서 비밀번호 설정 -> 출력됨

> 비밀번호 설정 값을 가져와야함
>
> 출력된 값을 변수로 가져와야함
>
> 그 변수를 gitlab.xml  





```shell
// temp.sh

PASSWORD=$(sed -n 's/^ *GITLABCREDENTIAL *=*//p' /var/jenkins_home/Data)
echo "import hudson.util.Secret
def password = "\"$PASSWORD\""
def secret = Secret.fromString(password)
println(secret.getEncryptedValue())" > /var/jenkins_home/temp.groovy

ENCRYPTED_PASSWORD=$(java -jar /bin/jenkins-cli.jar -s http://k3a201.p.ssafy.io:8282/ groovy =< /var/jenkins_home/temp.groovy)
echo $ENCRYPTED_PASSWORD

```

```groovy
// temp.groovy

import hudson.util.Secret
def password = "credential"
def secret = Secret.fromString(password)
println(secret.getEncryptedValue())

```





* 그루비 파일을 실행함

```shell
java -jar /bin/jenkins-cli.jar -s http://k3a201.p.ssafy.io:8282/ groovy =< ./gitcredential.groovy

```



* 그루비 사용

```groovy
// 복호화

println(hudson.util.Secret.decrypt("{AQAAABAAAAAwlGBrc02snzrQn+M58w/OzGpyJKDwtpLEeqySUf2V8ScC6MS1crCKFzwZw37gZjOylik7el7PLEQb2TcRCkKq5Q==}"))
```





### 내일 할 일

/var/jenkins_home 으로 다 옮기기

현수랑 변수명 회의하기

http:// 부분 커스터마이징 가능하게 짜기 > groovy 부분

