FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon
EXPOSE 8080
RUN cp $(find build/libs -maxdepth 1 -name '*.jar' ! -name '*-plain.jar' -print -quit) app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]