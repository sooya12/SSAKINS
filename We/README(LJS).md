# 10/27 삽질

## PGP 공개키 에러

![image](https://user-images.githubusercontent.com/22046757/97255120-468e1180-1853-11eb-8c19-22fdbe4011dc.png)

> 패키지 설치 중 오류발생 (sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable")
>
> 이후 sudo apt-get update를 해도 안됨
>
> 원인 : 오류 메시지에 표시된 PGP 공개키가 서버에 등록되지 않아서 발생
>
> 해결 : sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 7EA0A9C3F273FCD8 명령어를 통해 공개키를 키 서버에 업로드 함.

<br>

## scp 인코딩 에러

![image](https://user-images.githubusercontent.com/22046757/97256484-77237a80-1856-11eb-99d8-100a90fab681.png)

> scp 명령어를 이용해 로컬-서버로 파일 전송 후 install.sh(쉘 스크립트 파일) 실행시 오류 발생
>
> 원인 :  운영체제 마다 개행문자 처리 방법이 다르기 때문에 발생



![image](https://user-images.githubusercontent.com/22046757/97256636-e8fbc400-1856-11eb-91e2-2b9eaceccb28.png)

> 해결 : dos2unix 명령어로 파일 형식을 유닉스 파일형식으로 변경

## 10/27 강의

- 현재 실행중인 컨테이너 나열

  ``` 
  docker ps(process status)
  ```

- 이미지 내부 파일 시스템 구조 보기

  ```
  docker run 이미지 이름 (ls)
  
  docker : 도커 클라이언트 언급
  run : 컨테이너 생성 및 실행
  이미지 이름 : 컨테이너를 위한 이미지
  (ls) : 원래 이미지가 가지고 있는 시작 명령어를 무시하고 여기에 있는 커맨드를 실행
       ls 커맨드는 현재 디렉토리의 파일 리스트 표출
  ```

- 컨테이너 속성

  ```
  CONTAINER ID : 컨테이터 고유한 ID 값
  IMAGE : 컨테이너 생성시 사용한 도커 이미지
  COMMAND : 컨테이너 시작시 실행될 명령어(대부분 이미지에 내장되어 있어서 설정X)
  CRAETED : 컨테이너 생성된 시간
  STATUS : 컨테이너의 상태(실행중 Up, 종료 Exited, 일시정지 Pause)
  PORTS : 컨테이너가 개방한 포트와 호스트에 현결한 포트
  NAMES : 컨테이너 고유한 이름 --name 옵션으로 이름을 설정하지 않으면 도커 엔진이 임의로 형용사와 명사를 조합해 설정, id와 중복X, 이름 변경 가능(docker rename original-name changed-name)
  ```

- 원하는 항목만 보기

  ```
  docker ps --format 'table{{.Names}}\table{{.Image}}'
  \t : 탭
  ```

- 모든 컨테이너 나열

  ```
  docker ps -a
  -a : all
  ```

  