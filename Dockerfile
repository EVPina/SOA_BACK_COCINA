FROM eclipse-temurin:17-jdk-alpine

# Crea un grupo y un usuario sin privilegios
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copia el archivo JAR generado por Maven. El * es un comodín.
COPY --chown=appuser:appgroup target/soacocina-0.0.1-SNAPSHOT.jar app.jar
# EXPONE el puerto que usará la aplicación (8085 para este servicio)
EXPOSE 8086

# ¡Cambia al usuario no-root!
USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]