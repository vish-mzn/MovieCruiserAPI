FROM java:8-jre
WORKDIR usr/src
ENV MYSQL_DATABASE=fse_movie_db
ENV MYSQL_USER=app_root
ENV MYSQL_PASSWORD=root123
ENV MYSQL_CI_URL=jdbc:mysql://localhost:3306/fse_movie_db
ADD ./target/moviecruiserserver-0.0.1-SNAPSHOT.jar /usr/src/moviecruiserserver-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "moviecruiserserver-0.0.1-SNAPSHOT.jar"]
