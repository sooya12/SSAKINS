sudo apt install unzip
wget http://15.165.161.87:8080/zip -O ssakins.zip && unzip -d ssakins ssakins.zip && rm ssakins.zip

# dos2unix로 개행문자 에러 처리
sudo apt install dos2unix
dos2unix ssakins/ssakins/install.sh

# install.sh 실행
sh ssakins/ssakins/install.sh