FROM openjdk:17-alpine

EXPOSE 8080

ARG JAR_FOLDER=build/libs
RUN apk add curl
ADD ${JAR_FOLDER}/spring-boot-loader/ ./
ADD ${JAR_FOLDER}/dependencies/ ./
ADD ${JAR_FOLDER}/snapshot-dependencies/ ./
ADD ${JAR_FOLDER}/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]