FROM openjdk:14

COPY /target/user-manager-*.jar /app.jar

ENTRYPOINT ["java","-jar", "app.jar"]