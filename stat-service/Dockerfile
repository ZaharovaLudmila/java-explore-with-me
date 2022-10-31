FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar statapp.jar
ENTRYPOINT ["java","-jar","statapp.jar"]