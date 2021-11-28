# Kalah Game (kalaha)

The backend for the game Kalaha using Java. The solution is deployed to AWS as a container in ECS - Fargate (serverless).

## Complete architecture

![Architecture](https://github.com/DaviRolim/kalah_frontend/blob/main/kalah-architecture.png?raw=true)

### Test

```bash
./gradlew test
```

### Run Locally

```bash
./gradlew bootRun
```

### Another Option to Run Locally

```bash
./gradlew bootRun
```

and then run

```bash
java -jar ./build/libs/kalaha_game-0.0.1-SNAPSHOT.jar
```
