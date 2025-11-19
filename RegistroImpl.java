import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class RegistroImpl extends UnicastRemoteObject implements Registro {
    
    private Map<String, Estudiante> estudiantes;
    private Random random;
    
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
                "INFORMACIÓN DEL ESTUDIANTE: \n" + "ID: %s\n" +"Nombre: %s\n" +"Carrera: %s\n" +"Semestre: %d\n" + "Correo: %s\n" +
                id, nombre, carrera, semestre, correo
            );
        }
    }

    public RegistroImpl() throws RemoteException {
        super();
        this.estudiantes = new HashMap<>();
        this.random = new Random();
    }
    
    private synchronized String generarIDUnico() {
        String id;
        do {
            StringBuilder sb = new StringBuilder();
            String caracteres = "MNBVUWOIWHI098765";
            for (int i = 0; i < 8; i++) {
                int index = random.nextInt(caracteres.length());
                sb.append(caracteres.charAt(index));
            }
            id = sb.toString();
        } while (estudiantes.containsKey(id));
        
        return id;
    }
    
    public String registrarEstudiante(String nombre, String carrera, int semestre, String correo) throws RemoteException {
        if (nombre == null) {
            return "Ingresa un nombre valido";
        }
        if (carrera == null) {
            return "Ingresa una carrera valida";
        }
        if (semestre < 1 || semestre > 5) {
            return "El semestre debe ser un numero del 1 al 5";
        }
        if (correo == null) {
            return "Ingresa un correo valido (por ejemplo distribuidas@gmail.com)";
        }

        for (Estudiante est : estudiantes.values()) {
            if (est.correo.equalsIgnoreCase(correo)) {
                return "Ya existe ese correo electronico " + est.id;
            }
        }
        String id = generarIDUnico();
        Estudiante nuevoEstudiante = new Estudiante(id, nombre, carrera, semestre, correo);
        estudiantes.put(id, nuevoEstudiante);
        String mensaje = "Estudiante registrado correctamente";
        return mensaje;
    }
    
    public String buscarEstudiantePorID(String id) throws RemoteException {

        if (id == null) {
            return "Ingresa un ID valido";
        }
        Estudiante estudiante = estudiantes.get(id);
        
        if (estudiante == null) {
            System.out.println(id + " no encontrado");
            //return "Estudiante no encontrado.\nEl ID " + id + " no existe en el sistema.";
        }
        
        System.out.println("Estudiante encontrado: " + id);
        return estudiante.toString();
    }
}
