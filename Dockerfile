FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8081

ARG JAR_FILE=target/auth-service-1.0.jar
ADD ${JAR_FILE} auth-service.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","auth-service.jar"]