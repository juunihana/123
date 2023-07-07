FROM maven:3.6-jdk-11-openj9 AS build
COPY . /home/spring/
RUN mvn -f /home/spring/pom.xml clean package

FROM openjdk:11
COPY --from=build /home/spring/target/*.jar /home/spring/app.jar
WORKDIR /home/spring
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/home/spring/app.jar"]
