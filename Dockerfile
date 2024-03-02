FROM openjdk:11
EXPOSE 8080
ADD target/springbootmvc-retailstore-img.jar springbootmvc-retailstore-img.jar
ENTRYPOINT ["java", "-jar", "springbootmvc-retailstore-img.jar"]