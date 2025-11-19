# Define la imagen base de Java
FROM openjdk:26-ea-trixie

# Copia los archivos al contenedor
COPY *.java /app/

# Establece el directorio de trabajo
WORKDIR /app

# Compila los archivos Java
RUN javac *.java

# Expone el puerto utilizado por RMI
EXPOSE 1099

# Inicia el servidor RMI al ejecutar el contenedor
CMD ["java", "ServidorRMI"]
