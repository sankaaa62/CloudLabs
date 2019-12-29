FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/dockerapp-1.0-SNAPSHOT.jar dockerapp-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom.","-jar","/dockerapp-1.0-SNAPSHOT.jar"]
