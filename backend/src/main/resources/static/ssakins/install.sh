echo "["`date`"] start to install SSAKINS."
sleep 1

echo "["`date`"] pull SSAKINS image from docker-hub... " 
sudo docker pull phm0127/ssakins
sleep 1
PORT=$(sed -n 's/^ *PORT= *=*//p' ./ssakins_home/accountInfo.sh)

echo -n "["`date`"] Set environments for SSAKINS ... "

REMOTE_DIRECTORY=$(pwd)/deploy
echo $REMOTE_DIRECTORY > ./ssakins_home/remoteDirectory

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

sudo docker run --name "ssakins" -u 'root' -v $SSAKINS_HOME/ssakins_home/:/var/jenkins_home/ -p $PORT:8080 -d phm0127/ssakins
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

echo -n "["`date`"] load on SSAKINS ...           "


printf ' '
STR=$(echo "초 남음")
for n in `seq 0 180` ; do
        N=`expr $n / 2`
        SEC=`expr 90 - $N`
        if [ $SEC -ge 10 ]; then
        printf '\b\b\b\b\b\b\b\b\b\b%d%s' "$SEC" "$STR"
elif [ $n -eq 162 ]; then
        printf '\b\b'
        printf '\b\b\b\b\b\b\b\b\b%d%s' "$SEC" "$STR"
else
        printf '\b\b\b\b\b\b\b\b\b%d%s' "$SEC" "$STR"
fi
        printf '%.1s' "$sp"
    sleep 0.5
    sp=${sp#?}${sp%???}
done



sudo docker exec -u root ssakins bash /var/jenkins_home/ssakins-setting.sh
sudo docker restart ssakins



