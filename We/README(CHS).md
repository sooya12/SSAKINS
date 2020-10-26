

## :calendar: 20.10.14

#### :black_nib: apt-get 와 apt

Advanced Packaging Tool, APT

core library와 함께 동작하는 free software 사용자 인터페이스

> free software : 복사, 사용, 연구, 수정, 배포 등의 제한이 없는 sw (open source software)

Debian GNU/Linux 배포판 계열 배포판에서 sw를 설치/제거하는 일을 함

sw package의 확인, 구성, 설치를 자동화함으로써 unix 계열 컴퓨터 시스템 상의 sw를 관리하는 작업을 단순하게 만듦

```shell
// 패키지 설치
$ sudo apt insatll [packageName]
$ sudo apt-get install [packageName]

// 키워드로 패키지 검색
$ apt search [keyword]
// 패키지 정보 조회
$ apt show [packageName]

// 패키지 설치 중 문제 발생 시 / 새로운 버전 설치 시
$ apt-get --reinstall install [packageName]

// 패키지 제거
$ apt-get remove [packageName]
// 패키지 제거 + configuration file까지 완벽하게 제거
$ apt-get --purge remove [packageName]

// 패키지 업그레이드
$ apt-get upgrade
// 패키지 업그레이드 + 업그레이드 내역
$ apt-get -u upgrade

// 사용하지 않는 패키지 제거
// /var/cache/apt/archives와 /var/cache/apt/archives/partial/에 없는 패키지 제거
$ apt-get clean
// 패키지의 구 버전만 제거
$ apt-get autoclean
```

apt-get과 달리 apt는 진행도를 보여줌 (시각적)



#### :black_nib: echo와 cat, more

- echo

  - unix 계열 OS에서 문자열을 컴퓨터 터미널에 출력하는 명령어

  - shell script와 배치 파일에서 화면이나 파일로 상황을 알리는 문자열을 출력할 때 사용

    ```shell
    // 문자열 출력
    $ echo [문자열]
    
    // 여러 줄 출력
    $ echo -e [문자열]\\n[문자열]
    
    // 문자열이 저장된 파일 생성/덮어쓰기
    $ echo "[문자열]" > [파일 경로] 
    
    // 기존 내용 + 문자열 저장
    $ echo [문자열] >> [파일 경로]
    ```

- cat

  - 파일들을 연결하고 표시하기 위해 사용되는 표준 unix 프로그램

  - catenate (연결하다) 유래

  - 파일이 길어서 한 화면을 벗어나는 경우, 자동 스크롤. 파일 앞부분 말고 마지막 부분만 볼 수 있음

    ```shell
    // 파일 내용 출력
    $ cat [파일명]
    
    // 파일 병합
    $ cat xaa xab xac > [파일명]
    $ cat xa[a-c] > [파일명]
    ```

- more

  - cat과 달리 화면 스크롤이 계속 진행되지 않음. 한 화면만큼씩 보여줌

  - spaceBar로 다음 화면을 보여줌

    ```shell
    $ more [파일명]
    ```



#### :black_nib: Docker

```shell
// 도커 구 버전 삭제
$ sudo apt-get remove docker docker-engine docker.io containerd runc

// apt 패키지 업데이트
$ sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common

// 도커 공식 GPG 키 추가
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
$ sudo apt-key fingerprint 0EBFCD88

// stable repository 설정
$ sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

// 도커 엔진 설치
$ sudo apt-get update
$ sudo apt-get install docker-ce docker-ce-cli containerd.io

// 도커 엔진 버전 확인
$ apt-cache madison docker-ce

// 도커 엔진 특정 버전 설치
$ sudo apt-get install docker-ce=[version] docker-ce-cli=[version] containerd.io
// hello-world 이미지를 통해 설치 확인
$ sudo docker run hello-world
```



## :calendar: 20.10.15

#### :black_nib: AWS ubuntu 18.04의 Docker에 백엔드 빌드

백엔드 root에 Dockerfile 생성

```dockerfile
FROM openjdk:8-jdk-alpine
LABEL maintainer="[소유자명]"
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```



ubuntu에 빌드 파일 옮길 폴더 생성

```shell
// ubuntu에 backend 폴더 생성
$ mkdir backend

// backend maven build
$ mvn package
```



로컬에서 백엔드 빌드 후 ubuntu로 전송

```shell
// build된 jar 파일을 로컬에서 ubuntu로 전송
$ scp -i [pem 경로] -r [프로젝트 경로]/backend/target/*.jar ubuntu@[퍼블릭 DNS]:~/backend

// Dockerfile을 로컬에서 ubuntu로 전송
$ scp -i [pem 경로] -r [프로젝트 경로]/backend/Dockerfile ubuntu@[퍼블릭 DNS]:~/backend
```



ubuntu에서 docker  image 실행 후 nginx 재시작

```shell
// 도커 image 빌드
~/backend$ sudo docker build -t backend .

// 도커 image 확인
$ sudo docker images

// 도커 실행
$ sudo docker run -d -p 8080:8080 backend

// 도커 이름 확인
$ sudo docker ps -a

// 도커 이름으로 log 확인
$ sudo docker logs [도커 이름]

// nginx 재시작
$ sudo service nginx restart
```



#### :black_nib: nginx 설치 및 설정

```shell
// nginx 설치
$ sudo apt-get install nginx

// nginx 버전 확인
$ nginx -v

// 시작
$ sudo service nginx start
$ sudo systemctl start nginx
$ sudo /etc/init.d/nginx start

// 재시작
$ sudo service nginx restart
$ sudo systemctl restart nginx
$ sudo /etc/init.d/nginx restart

// 중지
$ sudo service nginx stop
$ sudo systemctl stop nginx
$ sudo /etc/init.d/nginx stop

// 상태
$ sudo service nginx status
$ sudo systemctl status nginx

// 설정 reload
$ sudo service nginx reload
$ sudo systemctl reload nginx
$ sudo nginx -s reload

// configuration file syntax check
$ sudo nginx -t
```



nginx default 파일 수정

```shell
$ cd /etc/nginx/sites-available
$ sudo vi default

// default 파일

server {
        listen 80 default_server;
        listen [::]:80 default_server;

        # Frontend 설정
        root /var/www/html/dist;        # Front 빌드 파일 위치

        index index.html index.htm;     # index 파일명

        server_name _;                  # 서버 도메인

        location / {
                try_files $uri $uri/ /index.html;
        }


        # Backend Proxy 설정
        location /[api] {
                proxy_pass http://localhost:8080;
                proxy_redirect off;
                charset utf-8;

                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_set_header X-NginX-Proxy true;
        }
}
```



#### :black_nib: AWS ubuntu 18.04에 프론트엔드 빌드

로컬에서 프론트엔드 빌드

```shell
$ npm run build

// 로컬에서 dist 폴더 ubuntu로 전송
$ scp -i [pem 경로] -r [프로젝트 경로]/frontend/dist ubuntu@[퍼블릭 DNS]:~/dist
```



ubuntu에서 dist 폴더 이동

```shell
$ sudo mv ~/dist /var/www/html/dist
```



## :calendar: 20.10.16

#### :black_nib: Jenkins

[Linux + Jenkins 설치 Doc](https://pkg.jenkins.io/debian-stable/)

ubuntu에 Jenkins 설치

```shell
$ wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -

// 설치를 위해 sources.list에 접근
$ cd /etc/apt
$ sudo vi sources.list

// sources.list에 추가
deb https://pkg.jenkins.io/debian-stable binary/ 

// Jenkins 설치
$ sudo apt-get update
$ sudo apt-get install jenkins
```



Jenkins 설치 중 에러 발생

- ![image-20201016131042747](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201016131042747.png)

- Jenkins에서는 Java 8 사용

  ```shell
  $ sudo apt install openjdk-8-jre
  $ java -version
  ```



이어서 Jenkins 설치 후, Jenkins 접속

```shell
$ sudo apt-get install jenkins

// Jenkins 기본 포트 8080을 8082로 변경
$ sudo vi /etc/default/jenkins

// jenkins
HTTP_PORT=8082

// Jenkins 서비스 시작
$ sudo service jenkins start

// Jenkins.war 위치 찾기
$ find / -name "jenkins.war"
// /usr/share/jenkins/jenkins.war 에 있음을 찾음

// Jenkins 접속
$ java -jar /usr/share/jenkins/jenkins.war --httpPort=8082
```



발급받은 패스워드로 [퍼블릭 DNS]:8082 접속해서 Jenkins 실행

> 18466a11ca92435fbc55d369c3e6bc6c
> admin / blockchallen 



## :calendar: 20.10.21

#### :black_nib: Jenkins로 무중단 배포 설정하기

webhook을 사용하여 git이랑 Jenkins 연동

```shell
// Jenkins 작업공간 확인
$ cd /var/lib/jenkins/workspace/
```



젠킨스에서 frontend, backend npm이랑 maven으로 build

> ##### Jenkins 관리 - Global Tool Configuration
>
> - JDK
>
>   - JAVA_HOME 설정
>
>     $ echo $JAVA_HOME으로 얻은 경로 추가
>
>     ```shell
>     // root 권한 획득
>     $ sudo su
>     
>     // javac 경로 확인
>     $ readlink -f /usr/bin/javac
>     
>     // JAVA_HOME, PATH 설정
>     $ vi ~/.bashrc
>     
>     // ~/.bashrc
>     JAVA_HOME=$(readlink -f /usr/bin/javac | sed "s:/bin/javac::")
>     export JAVA_HOME
>     PATH=$PATH:$JAVA_HOME/bin
>     export PATH
>     
>     $ source ~/.bashrc
>     $ echo $JAVA_HOME
>     ```
>
> - Maven
>
>   - Install automatically 선택해서 없으면 자동 설치되게 하기
>
> - NodeJS
>
>   - Install automatically 선택

> ##### Item - 구성
>
> - 빌드 유발
>
>   - Gitlab 사용 시, Build when a change is pushed to Gitlab 선택
>   - GitHub hook trigger for GITScm polling 으로 webhook 발생 시, 빌드되게 설정
>
> - 빌드 환경
>
>   - npm 사용 위해서 Provide Node & npm bin/ folder to PATH 선택
>
> - Build
>
>   - Invoke top-level Maven targets
>
>     - Maven 사용해서 backend 빌드 시, 사용
>     - Maven version은 Global Tool Configuration에서 설정한 것 사용
>     - Goals는 빌드하려면 package
>
>   - Execute shell
>
>     - npm 사용해서 frontend 빌드 시, 사용
>
>       ```shell
>       cd frontend
>       npm install
>       npm run build
>       ```
>
>   - Execute shell
>
>     - application.properties 없으면 서버에 넣어주기 위해서 사용
>
>       ```shell
>       echo "spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
>       spring.datasource.url=jdbc:mysql://[DB IP]:3306/[Database명]?allowPublicKeyRetrieval=true&useSSL=false
>       spring.datasource.username=[UserName]
>       spring.datasource.password=[Password]
>       spring.jpa.hibernate.ddl-auto=update" > backend/src/main/resources/application.properties
>       ```
>
> - 빌드 후 조치
>
>   - 빌드된 파일들을 SSH로 Jenkins 작업 공간에서 서버 특정 경로로 옮기기
>
>   - .jar, dist/, Dockerfile (있으면)
>
>   - Send build artifacts over SSH
>
>     - Transfers
>
>       > Source files : 이동시킬 파일/폴더 경로 (frontend/dist/ 또는 backend/target/*.jar)
>       >
>       > Remove prefix : Source files에서 제거할 prefix (frontend/ 또는 backend/target)
>       >
>       > Remote directory : 이동될 경로 (deploy)
>       >
>       > Exec command : 이동하고 나서 실행시킬 명령어 (.sh 실행)



Dockerfile 없으면 생성

```shell
// Dockerfile

FROM openjdk:[버전]
ARG JAR_FILE=./*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dserver.port=[서버 Port]", "app.jar"]
```



nginx에서 upstream으로 무중단배포 설정

```shell
$ cd /etc/nginx/sites-available
$ vi default

// default

upstream [UpstreamName] {
	least_conn; // 클라이언트 연결 개수가 적은 서버로 전달
	server 127.0.0.1:8081 weight=5 max_fails=3 fail_timeout=10s;
	server 127.0.0.1:8082 weight=10 max_fails=3 fail_timeout=10s;
}

server {
	listen 8080 default_server;
	listen [::]:8080 default_server;
	server_name _;
	
	location / {
		proxy_pass http://[UpstreamName];
	}
}
```



8081, 8082 포트에 따라서 sh파일 구분

> 도커파일이랑 .jar가 있는 경로에서 도커이미지 생성 및 도커이미지 실행
>
> port forwarding으로 8081/8082 전부 8080으로 접속
>
> ```shell
> // docker81.sh
> 
> docker build -t [dockerImageNameA] .
> docker run --name "[dockerImageNameA]" -p 8081:8080 -d [dockerImageNameA]
> 
> 
> // docker82.sh
> 
> docker build -t [dockerImageNameB] .
> docker run --name "[dockerImageNameB]" -p 8082:8080 -d [dockerImageNameB]
> ```



무중단 배포하기 위해서 실행 중인 port(8081 / 8082)에 따라 sh파일 선택

```shell
// dockermu.sh

D81_IS_RUNNING=$(docker ps -a | grep [dockerImageNameA])
D82_IS_RUNNING=$(docker ps -a | grep [dockerImageNameB])
	if [ "$D81_IS_RUNNING" ]; then
		echo "8082 포트로 실행"
		sh docker82.sh
		
		sleep 10
		
		echo "8081 포트 종료"
		docker stop [dockerImageNameA]
		docker rm [dockerImageNameA]
		docker rmi [dockerImageNameA]
	else
    	echo "8081 포트로 실행"
    	sh docker81.sh
    	
    	sleep 10
    	
    	if [ "$D82_IS_RUNNING" ]; then
    		echo "8081 포트 종료"
    		docker stop [dockerImageNameB]
			docker rm [dockerImageNameB]
			docker rmi [dockerImageNameB]
		fi
	fi
```



## :calendar: 20.10.26

#### :black_nib: Jenkins docker로 설치

```shell
$ sudo docker pull jenkins/jenkins:lts
$ sudo docker run -d --name "test" -p 8090:8080 jenkins/jenkins:lts

```

```shell
$ sudo su
$ docker exec test cat /var/jenkins_home/secrets/initialAdminPassword

// docker 컨테이너 터미널에 bash 접속
// 컨테이너는 비어 있다. 아무것도 없다. 필요한 것만 깔려있다. 근데 내가 필요한건 없다.
$ docker exec -it test /bin/bash

// 루트 계정 접속
$ docker exec -u root -it test /bin/bash

```



#### :black_nib: Jenkins 잠금 해제

```shell
// 루트 계정 접속
$ docker exec -u root -it test /bin/bash

$ apt-get update
$ apt-get upgrade
$ apt-get install vim
$ vi /var/jenkins_home/config.xml
```

config.xml에서 true를 false로 해서 잠금 해제

```xml
<useSecurity>false</useSecurity>
```

![image-20201026152526142](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201026152526142.png)

![image-20201026152526142](/uploads/48c4a05142d426d99a2d4f235b6b6e02/image-20201026152526142.png)



#### :black_nib: jenkins-cli.jar 없을 때 설치

```shell
// /root/bin 경로에 jenkins.cli.jar 설치됨
$ wget -P ~/bin [Jenkins 경로]/jnlpJars/jenkins-cli.jar

// jenkins-cli.jar 명령어 확인
// 브라우저로 Jenkins 접속해서 jenkins 관리 - Jenkins CLI 에 있음
$ java -jar ~/bin/jenkins-cli.jar -s [Jenkins 경로] -webSocket help
```



#### :black_nib: jenkins-cli로 Plugin 설치

```shell
$ java -jar ~/bin/jenkins-cli.jar -s http://localhost:8080/ install-plugin [plugin명] -deploy -restart

// NodeJS 설치
$ java -jar ~/bin/jenkins-cli.jar -s http://localhost:8080/ install-plugin NodeJS -deploy -restart

// Maven 설치
$ java -jar ~/bin/jenkins-cli.jar -s http://localhost:8080/ install-plugin maven-plugin -deploy -restart

// Publish-Over-SSH 설치
$ java -jar ~/bin/jenkins-cli.jar -s http://localhost:8080/ install-plugin publish-over-ssh -deploy -restart
```

