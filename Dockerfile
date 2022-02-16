FROM adoptopenjdk/openjdk11

EXPOSE 9090

ADD target/kms-spring-practice.jar kms-spring-practice.jar

ENTRYPOINT ["java","-jar","kms-spring-practice.jar"]