import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;


public class RegistroImpl extends UnicastRemoteObject implements Registro {
    
    private ArrayList<Estudiante> estudiantes;
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
        this.estudiantes = new ArrayList<>();
        this.random = new Random();
    }
    
    private synchronized String generarIDUnico() {
        String id;
        do {
            StringBuilder sb = new StringBuilder();
            String caracteres = "QWERTYUIOP1234567890";
            for (int i = 0; i < 8; i++) {
                int index = random.nextInt(caracteres.length());
                sb.append(caracteres.charAt(index));
            }
            id = sb.toString();
        } while (existeID(id));
        
        return id;
    }
    
    private boolean existeID(String id) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).id.equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    private Estudiante buscarPorID(String id) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).id.equals(id)) {
                return estudiantes.get(i);
            }
        }
        return null;
    }
    
    private boolean existeCorreo(String correo) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).correo.equalsIgnoreCase(correo)) {
                return true;
            }
        }
        return false;
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

        if (existeCorreo(correo)) {
            return "Ya existe ese correo electronico";
        }
        
        String id = generarIDUnico();
        Estudiante nuevoEstudiante = new Estudiante(id, nombre, carrera, semestre, correo);
        estudiantes.add(nuevoEstudiante);
        String mensaje = "Estudiante registrado correctamente";
        return mensaje;
    }
    
    public String buscarEstudiantePorID(String id) throws RemoteException {

        if (id == null) {
            return "Ingresa un ID valido";
        }
        
        Estudiante estudiante = buscarPorID(id);
        
        if (estudiante == null) {
            String mensaje = "ID no encontrado" + id;
            return mensaje;
        }
        System.out.println("Estudiante encontrado: " + id);
        return estudiante.toString();
    }
}
