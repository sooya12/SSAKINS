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





