# Usa una imagen base de Java 17
FROM openjdk:17-jdk-slim

# Copia el archivo JAR generado por Maven
COPY target/ms-anti-fraud-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8081 (o el puerto que uses en tu aplicación)
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]