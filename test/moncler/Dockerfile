FROM maven:3.6-jdk-8-alpine AS build
COPY . /home/spring/
RUN mvn -f /home/spring/pom.xml clean package

FROM openjdk:8-alpine
COPY --from=build /home/spring/target/*.jar /home/spring/app.jar
COPY --from=build /home/spring/*.p12 /home/spring/key.p12
COPY --from=build /home/spring/Inbound /home/spring/Inbound/
COPY --from=build /home/spring/Outbound /home/spring/Outbound/
COPY --from=build /home/spring/template.xlsx /home/spring/template.xlsx
WORKDIR /home/spring
RUN chmod -R 777  /home/spring/Inbound
RUN chmod -R 777  /home/spring/Outbound
RUN chmod -R 777 /home/spring/template.xlsx
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/spring/app.jar"]
