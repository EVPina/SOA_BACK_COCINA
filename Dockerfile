FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copia el archivo JAR generado por Maven. El * es un comodín.
COPY target/soacocina-0.0.1-SNAPSHOT.jar app.jar
# EXPONE el puerto que usará la aplicación (8085 para este servicio)
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]