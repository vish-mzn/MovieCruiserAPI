cd authenticationserver
source ./env-variable.sh
mvn clean package

#docker build -t user-app .

cd ..
cd moviecruiserserver
source ./env-variable.sh
mvn clean package

#docker build -t movie-app .
cd ..