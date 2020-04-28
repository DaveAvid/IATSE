FROM openjdk:8-jdk-alpine
COPY target/IATSE-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 443
EXPOSE 80
ENTRYPOINT ["java","-jar","/app.jar"]
