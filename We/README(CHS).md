## :calendar: 20.10.14

#### :black_nib: apt-get 와 apt

Advanced Packaging Tool, APT

core library와 함께 동작하는 free software 사용자 인터페이스

> free software : 복사, 사용, 연구, 수정, 배포 등의 제한이 없는 sw (open source software)

Debian GNU/Linux 배포판 계열 배포판에서 sw를 설치/제거하는 일을 함

sw package의 확인, 구성, 설치를 자동화함으로써 unix 계열 컴퓨터 시스템 상의 sw를 관리하는 작업을 단순하게 만듦

```apt
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

    ```echo
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

    ```cat
    // 파일 내용 출력
    $ cat [파일명]
    
    // 파일 병합
    $ cat xaa xab xac > [파일명]
    $ cat xa[a-c] > [파일명]
    ```

- more

  - cat과 달리 화면 스크롤이 계속 진행되지 않음. 한 화면만큼씩 보여줌

  - spaceBar로 다음 화면을 보여줌

    ```more
    $ more [파일명]
    ```



#### :black_nib: Docker

```docker
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
$ sudo apt-get install docker-ce docer-ce-cli containerd.io

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

