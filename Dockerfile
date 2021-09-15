# We need a JDK 11 image, so specify that we want to build from a JDK 11 image
FROM adoptopenjdk/openjdk11:alpine
# Specify a default work directory where the app and its files will be stored
WORKDIR /opt
# Copy your packaged JAR file into your image
COPY target/spring-boot-todo-demo-0.0.1-SNAPSHOT.jar app.jar
# Specify an entrypoint on which executable your image should start on. We are building an image based on a Java application so we will need to use "java".
ENTRYPOINT ["java", "-jar", "app.jar"]