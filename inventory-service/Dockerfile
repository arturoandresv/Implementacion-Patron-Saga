# Etapa 1: Build
FROM maven:3.9.8-eclipse-temurin-21 AS builder

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de configuración de Maven
COPY pom.xml .

# Descarga dependencias (esto se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copia el código fuente
COPY src ./src

# Compila el proyecto y empaqueta el JAR
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo .jar desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8083

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]