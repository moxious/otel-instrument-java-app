FROM openjdk:22-slim-bullseye
WORKDIR /app
COPY target/spring-boot-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
