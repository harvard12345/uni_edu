FROM openjdk:11
ADD target/demo-0.0.1-SNAPSHOT.jar uniedu.jar
ENTRYPOINT ["java","-jar","uniedu.jar"]