### 감자라네

--------------------------



## 빌드&배포 공부

### AWS에서 빌드&배포

#### Front

###### apt 패키지 매니저로 npm 설치

```shell
sudo apt-get update
sudo apt-get install nodejs // 이건 없어도 되나..?
sudo apt-get install npm
```



###### Vue 프로젝트 빌드

``` shell
sudo npm install -g @vue/cli  // 이건 꼭 해야 하는 건가..?

npm install
npm run build
```

> AWS 사양이 낮아서 그런지 빌드가 자꾸 안 됨...
>
> 그런 연유로 로컬에서 빌드해서 scp 전송함
>
> bash에서 경로를 입력하는게 어려워서(내가 몰라서) 빌드한 위치로 가서 전송함!

```bash
scp -i ~/Documents/pem/CICDtest.pem -r ./dist/ ubuntu@34.192.35.57:/home/ubuntu/
```

[scp 파일 전송 참고](https://www.sallys.space/blog/2017/11/28/aws-scp/)



###### NginX 설치

> nginx 설치

```shell
sudo apt-get update
sudo apt-get upgrade
sudo apt-get install nginx
```

> conf 파일 설정

```shell
cd /etc/nginx/sites-availables
```

> default 파일 수정 (블록챌린꺼는 따로 드라이브에 저장해 둠. SSL 적용된거라 모르겠어 껄껄)

``` shell
sudo vi default

root /var/www/html/dist; 로 수정해주자 는 왜지..?
```



###### 배포

> scp로 받아온 dist 폴더 nginx로 통해서 배포하려고 복사?

```shell
cp -r dist /var/www/html cp -r dist /var/www/html 
```



> nginx 실행

```shell
sudo service nginx start
sudo service nginx restart // 이미 실행중이라면
```

> nginx 상태 확인

```shell
service nginx status
또는
systemctl status nginx
```

> 초록색 active (running) 확인



[SSL 적용 참고](https://shinjongpark.github.io/2020/02/17/AWS-nginx-vue-spring-ssl.html)



#### Back

###### 자바 설치 및 설정

> 자바 설치

```shell
sudo apt-get install openjdk-8-jre
sudo apt-get install openjdk-8-jdk
```



> 자바 설정

```shell
sudo vi /etc/profile
```

위 파일 안에 다음 내용을 추가

```
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
export CLASS_PATH=$JAVA_HOME/lib:$CLASS_PATH
```



> 설정 리로딩

```shell
source /etc/profile
```



###### 메이븐 설치 및 설정

> 메이븐 설치

```shell
wget http://mirror.navercorp.com/apache/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz // Maven 다운로드
tar xvf apache-maven-3.5.4-bin.tar.gz // 압축 해제
sudo mv apache-maven-3.5.4 /opt // /opt 디렉토리 밑으로 이동
sudo ln -s /opt/apache-maven-3.5.4 /opt/maven // /opt/maven 링크 설정
```



> 메이븐 설정

```shell
sudo vi /etc/profile
```

위 파일 안에 다음 내용을 추가

```
export M2_HOME=/opt/maven
export PATH=$PATH:$M2_HOME/bin
```



> 설정 리로딩

```shell
source /etc/profile
```



> 난 이 설정은 안 했던 것 같은디

```shell
vi /opt/mave/conf/settings.xml
```

위 파일 안에 다음 내용을 추가

```
<!-- localRepository
 | The path to the local repository maven will use to store artifacts.
 |
 | Default: ~/.m2/repository
-->
<localRepository>/data/repository/maven</localRepository>
```



> 설정 리로딩

```
source /etc/profile
```



###### 빌드 및 실행

> 백엔드 파일로 이동 (mvnw 같은 것 있는 위치) 후 빌드 빌드할 때 target폴더가 있어야(?) 빌드 되더라...

```
mvn package
```

> 프로젝트 실행 (target 상위 폴더에 있었음 그래서 경로 설정 해줌)

```
java -jar target/blockchallen-0.0.1-SNAPSHOT.jar
```



#### DB

> DB는 도커로 돌렸다! 그래서 도커 설치

```shell
sudo apt-get update
sudo apt-get install docker.io // 하지만 난 이 방법을 사용했다.
sudo ln -sf /usr/bin/docker.io /usr/local/bin/docker // 이렇게 링크를 만들어서 사용하는 것이 좋대
```



###### MySQL

> MySQL 이미지 저장소에서 다운로드

```shell
sudo docker pull mysql
```

> 다운로드된 도커 이미지 확인

```shell
sudo docker images
```



> 이미지로 컨테이너 생성 및 실행

```shell
sudo docker run -d -p 3306:3306 --name mysql --env MYSQL_ROOT_PASSWORD="ssafy" mysql
```

> -d (--detach) : detached모드. 데몬모드. 컨테이너 백그라운드 실행 
>
> -p (--publish) (연결할 호스트의 포트 번호):(컨테이너 내부 포트 번호)
>
> --name : docker container 이름
>
> --env (-e) : 환경변수 값
>
> - MYSQL_ROOT_PASSWORD : mysql root 패스워드를 입력하는 환경 변수
> - MYSQL_USER : mysql user name을 입력하는 환경 변수
> - MYSQL_PASSWORD : mysql 패스워드를 입력하는 환경 변수
> - MYSQL_DATABASE : 생성할 데이터베이스 명



> docker mysql 접속

```shell
sudo docker exec -it mysql bash
```

> bash 쉘에서 mysql 어플리케이션 실행

```bash
mysql -u root -p
mysql -u root -pssafy // 비밀번호 바로 쳐서 들어가기
```



> Container 시작/중지/재시작/확인

```shell
sudo docker start mysql
sudo docker stop mysql
sudo docker restart mysql
sudo docker ps -a // -a 옵션을 빼면 실행 중인 컨테이너만 조회
```



### Docker 백엔드 배포

###### dockerBack 폴더 생성

```shell
mkdir dockerBack
```



###### docker 이미지 생성

> dockerBack 폴더에서 DockerFile 만들기

```shell
vi DockerFile
```

위 파일 안에 다음 내용 추가

```
FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```



>  백엔드 폴더가서 jar 파일 복사해오기

```shell
cp -r target/ ~/dockerBack
```



> dockerBack 폴더 에서 이미지 빌드

```shell
// backend 라는 이름으로 도커 이미지 빌드
sudo docker build -f DockerFile -t backend .
```

> 도커 image 확인 및 실행

``` shell
sudo docker images
sudo docker run -d -p 8080:8080 backend
```



> 도커 log 확인

```shell
sudo docker logs competent_clarke // 이름 지정 안 해서 이상하게 생김
```





### CI/CD

###### Jenkins 도커 이미지 사용

> Jenkins 이미지 다운로드

```shell
sudo docker pull jenkins/jenkins
```

> 잘 받아졌는 지 확인

``` shell
sudo docker images
```

> 컨테이너 실행

```shell
docker run -d -p 9090:8080 -v /jenkins:/var/jenkins_home --name jenkins -u root jenkins/jenkins
```

> -d : 백그라운드 실행
>
> -p : 내 서버 아이피 9090 포트로 접근하면 컨테이너 내부 포트 8080으로 매핑
>
> -v : 볼륨 옵션. 호스트와 볼륨을 공유하는 설정.
>
> ​	(내 호스트 서버의 루트 경로):(실제 컨테이너 내부의 젠킨스 폴더)
>
> ​	/jenkins:/var/jenkins_home 
>
> ​	젠킨스가 빌드하고 난 결과물들을 직접 호스트에서 공유할 수 있다.



=> aws 프리티어 사양이 너무 안 좋아서 도커를 여러개 돌리면 인스턴스가 뻗어버린다....



#### 20.10.21

- Jenkins로 CI 구축해보기
- sh 파일로 배포 관리
  - nginx로 프론트 배포
  - 백엔드 배포
  - 백엔드 도커로 배포
  - 무중단 배포를 위해 도커 컨테이너 두 개 생성해서 교차로 구동(8081, 8082) + nginx 포트 8080 으로 포트 포워딩 설정 



테스트 프로젝트 만들어서 혼자서 다시 해 보자!!!!



#### 20.10.26

- java로 Shell script 호출 실행
  - window 환경에서?
  - linux 환경에서?





#### CallBack 함수 / Arrow 함수

콜백 - 매개변수로 함수를 전달하거나 이벤트 처리 후 다시 호출되는 함수

[Arrow](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Functions/%EC%95%A0%EB%A1%9C%EC%9A%B0_%ED%8E%91%EC%85%98) - 익명함수의 일종

익명함수에서의 this는 가리키는 것이 다르다! 주의!