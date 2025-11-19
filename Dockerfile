# Define la imagen base de Java
FROM openjdk:26-ea-trixie

# Copia los archivos al contenedor
COPY *.java /ad_examen1_asanchez_rpadilla_amuñoz/

# Compila los archivos Java
RUN javac *.java

# Expone el puerto utilizado por RMI
EXPOSE 4000
CMD ["java", "ServidorRMI.java"]
