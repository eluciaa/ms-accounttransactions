FROM openjdk:17
EXPOSE 8088
ADD target/ms-movement-0.0.1-SNAPSHOT.jar ms-movement.jar
ENTRYPOINT ["java","-jar","/ms-movement.jar"]