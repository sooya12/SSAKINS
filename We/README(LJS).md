# 10/27 삽질

![image](https://user-images.githubusercontent.com/22046757/97255120-468e1180-1853-11eb-8c19-22fdbe4011dc.png)

> 패키지 설치 중 오류발생 (sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable")
>
> 이후 sudo apt-get update를 해도 안됨
>
> 원인 : 오류 메시지에 표시된 PGP 공개키가 서버에 등록되지 않아서 발생
>
> 해결 : sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 7EA0A9C3F273FCD8 명령어를 통해 공개키를 키 서버에 업로드 함.