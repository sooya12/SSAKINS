## :calendar: 20.10.26

#### :black_nib: docker image 만들기

~~원래는 dockerfile로 이미지를 만들어야하지만 Jenkins의 dockerfile이 장난없음..~~

[jenkins dockerfile 보러가기](https://github.com/jenkinsci/docker)

**그래서,** Jenkins 도커 이미지를 가지고와서 Customize를 해서 commit을 한 다음, docker-hub에 올려보기로 했음.

[docker commit 사용법 ](https://docs.docker.com/engine/reference/commandline/commit/)



jenkins container 내부에서 여러 설정들을 고치고 commit 을 한 다음에 [docker hub](https://hub.docker.com/repository/docker/phm0127/ssakins)에 이미지를 push함.

##### :no_entry: **그런데...** 이미지를 pull해서 컨테이너 실행 시 변경한 내용이 반영되지 않음...

##### vim만 깔림... (실화..?)

이유는 도커 이미지는 가장 상단의 레이어를 제외하곤 read-only였음... (문서를 다시 보니, commit은 image 보단 container용으로 사용하는 것 같다..)

[Docker's images and layers](https://docs.docker.com/storage/storagedriver/#images-and-layers)



처음으로 돌아가 dockerfile을 활용해서 만드는 방법이 있으나 dockerfile을 활용할 자신이 없음.

+ dockerfile을 안 쓰려고 하는 타당한 이유

  + ~~어렵다.~~

  + ~~길다.~~

  + ~~복잡하다.~~

  + ....

    

그렇다고 프로젝트를 포기할 수는 없으니 대안을 찾아봄. 구글링을 하다 volume이라는 기술을 활용해서 해볼 수 있겠다는 생각이 듦.

[Docker Volume](https://docs.docker.com/storage/volumes/)

docker container 구동할 때 ```-v``` 옵션으로 호스트 서버의 디렉토리와 컨테이너 디렉토리를 공유할 수 있음.

기존 컨테이너 내에 커스터마이징한 디렉토리들을 호스트 서버로 가지고와서 docker container 구동 시 이 디렉토리를 연동시켜주는 법을 해봐야겠다란 생각이 듦.

그런데, Container에서 host 디렉토리로 접근할 때 denied가 뜸.. 이유를 보니 컨테이너의 접근 권한 문제인 것 같아 ```-u``` 옵션을 통해 해결함. ~~일단 급한대로 root 권한을 줬지만 여유가 된다면 다른 방법을 찾아보는게 좋을듯..!~~  
[Docker의 uid, gid](https://medium.com/@mccode/understanding-how-uid-and-gid-work-in-docker-containers-c37a01d01cf)


성공...

![ssakins_CLI](https://user-images.githubusercontent.com/7456710/97186825-1f006000-17e5-11eb-82fc-681179154b81.gif)







## Script file

### install.sh

```shell
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

sudo docker run --name "ssakins" -u 'root' -v $SSAKINS_HOME/ssakins_home/:/var/jenkins_home/ -p 8000:8080 -d phm0127/ssakins
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

