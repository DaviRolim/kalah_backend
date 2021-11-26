FROM adoptopenjdk/openjdk11:alpine
WORKDIR /usr/src/kalah
COPY . .
RUN ./gradlew build
EXPOSE 8080
CMD ["java", "-jar", "./build/libs/kalaha_game-0.0.1-SNAPSHOT.jar"]