import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// Implementación de la interfaz remota Registro
public class RegistroImpl extends UnicastRemoteObject implements Registro {
    
    // Almacena los estudiantes: ID -> Estudiante
    private Map<String, Estudiante> estudiantes;
    private Random random;
    
    // Clase interna para representar un estudiante
    private static class Estudiante {
        String id;
        String nombre;
        String carrera;
        int semestre;
        String correo;
        
        public Estudiante(String id, String nombre, String carrera, int semestre, String correo) {
            this.id = id;
            this.nombre = nombre;
            this.carrera = carrera;
            this.semestre = semestre;
            this.correo = correo;
        }
        
        @Override
        public String toString() {
            return String.format(
                "INFORMACIÓN DEL ESTUDIANTE\n" +
                "ID: %s\n" +
                "Nombre: %s\n" +
                "Carrera: %s\n" +
                "Semestre: %d\n" +
                "Correo: %s\n" +
                id, nombre, carrera, semestre, correo
            );
        }
    }
    
    // Constructor de la implementación
    public RegistroImpl() throws RemoteException {
        super();
        this.estudiantes = new HashMap<>();
        this.random = new Random();
    }
    
    // Genera un ID único aleatorio de 8 caracteres
    private synchronized String generarIDUnico() {
        String id;
        do {
            // Genera un ID alfanumérico de 8 caracteres
            StringBuilder sb = new StringBuilder();
            String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            for (int i = 0; i < 8; i++) {
                int index = random.nextInt(caracteres.length());
                sb.append(caracteres.charAt(index));
            }
            id = sb.toString();
        } while (estudiantes.containsKey(id)); // Asegura que sea único
        
        return id;
    }
    
    // Registra un nuevo estudiante en el sistema
    public String registrarEstudiante(String nombre, String carrera, int semestre, String correo) throws RemoteException {
        // Validaciones
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Error: El nombre no puede estar vacío.";
        }
        
        if (carrera == null || carrera.trim().isEmpty()) {
            return "Error: La carrera no puede estar vacía.";
        }
        
        if (semestre < 1 || semestre > 12) {
            return "Error: El semestre debe estar entre 1 y 12.";
        }
        
        if (correo == null || !correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return "Error: El correo electrónico no es válido.";
        }
        
        // Verifica si el correo ya está registrado
        for (Estudiante est : estudiantes.values()) {
            if (est.correo.equalsIgnoreCase(correo)) {
                return "Error: El correo electrónico ya está registrado con ID: " + est.id;
            }
        }
        
        // Genera ID único
        String id = generarIDUnico();
        
        // Crea y guarda el estudiante
        Estudiante nuevoEstudiante = new Estudiante(id, nombre.trim(), carrera.trim(), semestre, correo.trim());
        estudiantes.put(id, nuevoEstudiante);
        
        System.out.println("Estudiante registrado exitosamente con ID: " + id);
        
        return String.format(
            "Estudiante registrado correctamente.\n" +
            "ID asignado: %s\n" +
            "Nombre: %s\n" +
            "Carrera: %s\n" +
            "Semestre: %d\n" +
            "Correo: %s\n" +
            "¡Guarda tu ID para futuras consultas!",
            id, nombre.trim(), carrera.trim(), semestre, correo.trim()
        );
    }
    
    // Busca un estudiante por su ID
    public String buscarEstudiantePorID(String id) throws RemoteException {
        // Validaciones
        if (id == null || id.trim().isEmpty()) {
            return "Error: El ID no puede estar vacío.";
        }
        
        String idBuscar = id.trim().toUpperCase();
        Estudiante estudiante = estudiantes.get(idBuscar);
        
        if (estudiante == null) {
            System.out.println("Búsqueda fallida: ID " + idBuscar + " no encontrado");
            return "Estudiante no encontrado.\nEl ID " + idBuscar + " no existe en el sistema.";
        }
        
        System.out.println("Búsqueda exitosa: ID " + idBuscar);
        return estudiante.toString();
    }
}
