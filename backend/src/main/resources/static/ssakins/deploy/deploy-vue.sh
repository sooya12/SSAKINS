VUE_SERVER=$(echo "ssakins-vue")
PORT=$(echo "80")
PORT_USED=$(sudo netstat -ntlp | grep ":$PORT " |awk '{printf $1}')
echo $PORT_USED
if [ $PORT_USED ];then
	echo "["`date`"] $PORT 포트가 이미 사용중이므로 관련  프로세스를 종료 후에 재실행하시거나 서버 포트 번호를 바꿔실행해야합니다."
	exit
fi
IS_RUNNING=$(sudo docker ps -a |grep $VUE_SERVER|awk '{printf $1}')
echo $IS_RUNNING

if [ -z $IS_RUNNING ]; then
	echo "["`date`"] nginx 서버를 설치합니다."
	#sudo docker build . -f ./Dockerfile-$VUE_SERVER -t $VUE_SERVER
	sudo docker run --name "$VUE_SERVER" -p 80:80 -p 443:443 -v $(pwd)/nginx.conf:/etc/nginx/conf.d/default.conf -d nginx
	#sudo docker run --name "$VUE_SERVER" -p 80:80 -p 443:443 -d nginx

	echo "["`date`" nginx 서버가 실행되었습니다."
else
	echo "["`date`"] 기존 vue 서버를 종료합니다."

fi
echo "["`date`"] vue서버를 재배포합니다."
sudo docker cp $(pwd)/dist $VUE_SERVER:/usr/share/nginx/html
echo "["`date`"] nginx를 재실행합니다."
sudo docker restart $VUE_SERVER
sleep 2
echo "["`date`"] Vue 서버 배포 완료"


