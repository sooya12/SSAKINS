echo "["`date`"] 스프링 서버 환경을 검사합니다."
SPRING_SERVER=$(echo "ssakins-spring")
#PORT=$(echo "8080")
PORT=$(sed -n 's/^ *BACKPORT *=*//p' ./ssakins_home/accountInfo)
IS_RUNNING=$(sudo docker ps -a |grep $SPRING_SERVER|awk '{printf $1}')

if [ -z $IS_RUNNING ]; then
	echo "["`date`"] 현재 구동중인 기존 서버가 없으므로 새로 실행합니다."
else
	echo "["`date`"] 현재 기존 서버가 구동중이므로 종료 후  재실행합니다."
	
	sudo docker stop $SPRING_SERVER
	sudo docker rm $SPRING_SERVER
	sudo docker rmi $SPRING_SERVER
	echo -n "["`date`"] Spring 서버를 종료합니다."	 

	printf ' '
	for n in `seq 0 20` ; do
	    printf '\b%.1s' "$sp"
	    sleep 0.5
	    sp=${sp#?}${sp%???}
	done

fi
echo "["`date`"] Spring 서버를 실행합니다."
sudo docker build . -f ./Dockerfile-$SPRING_SERVER -t $SPRING_SERVER
sudo docker run --name "$SPRING_SERVER" -d -p ${PORT}:8080 $SPRING_SERVER

echo "["`date`"] 성공적으로 종료"

