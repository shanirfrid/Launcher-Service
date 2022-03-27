FROM openjdk:latest
COPY build/libs/launcher-service-0.0.1-SNAPSHOT.jar launcher-service-0.0.1
ENTRYPOINT ["java","-jar","launcher-service-0.0.1"]
