# Imagen base con Java 17
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Copiar el jar construido
COPY target/*.jar app.jar

# Puerto que expone la app
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java","-jar","app.jar"]
