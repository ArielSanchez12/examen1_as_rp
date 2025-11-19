import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorRMI {
    
    private static int PUERTO = 1099;
    private static String NOMBRE_SERVICIO = "RegistroEstudiantes";
    
    public static void main(String[] args) {
        try {
            System.out.println("Registrar un estudiante en el RMI");
            System.out.println();
            
            System.out.println("Hola desde el servidor");
            RegistroImpl registroImpl = new RegistroImpl();
            Registry registry = LocateRegistry.createRegistry(PUERTO);
            
            // Vincula el objeto remoto con un nombre en el registro
            registry.rebind(NOMBRE_SERVICIO, registroImpl);
            

            System.out.println();
            System.out.println("Esperando peticiones");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
