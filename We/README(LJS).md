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

> 해결 : dos2unix 명령어로 파일 형식을 유직스 파일형식으로 변경