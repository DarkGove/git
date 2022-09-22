FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine-slim
ARG JAR_FILE=src/image1.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]