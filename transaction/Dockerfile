FROM eclipse-temurin:17-jre
WORKDIR /app
COPY build/libs/*.jar transaction.jar
ENTRYPOINT ["java", "-jar", "transaction.jar"]