FROM openjdk:11
EXPOSE 8080
ADD target/springboot-servlet-calculator-img.jar springboot-servlet-calculator-img.jar
ENTRYPOINT ["java", "-jar", "springboot-servlet-calculator-img.jar"]