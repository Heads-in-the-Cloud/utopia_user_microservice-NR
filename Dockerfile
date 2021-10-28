FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV DB_HOST=host.docker.internal
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]