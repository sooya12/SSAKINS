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





