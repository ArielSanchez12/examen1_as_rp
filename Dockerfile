# Define la imagen base de Java
FROM openjdk:26-ea-trixie

# Establece el directorio de trabajo (debe ser igual al nombre de la carpeta)
WORKDIR /ad_examen1_asanchez_rpadilla_amuñoz 

# Copia los archivos al contenedor
COPY *.java ./

# Compila los archivos Java
RUN javac Registro.java RegistroImpl.java ServidorRMI.java ClienteRMI.java

# Expone el puerto utilizado por RMI
EXPOSE 4000
CMD ["java", "ServidorRMI"]
