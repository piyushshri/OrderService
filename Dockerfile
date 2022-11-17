FROM openjdk:8
EXPOSE 8080
ADD target/OrderService-0.0.1-SNAPSHOT.jar OrderService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/OrderService-0.0.1-SNAPSHOT.jar"]