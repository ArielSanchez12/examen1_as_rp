import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// Servidor RMI para el sistema de registro de estudiantes
public class ServidorRMI {
    
    // Puerto RMI estándar
    private static final int PUERTO = 1099;
    private static final String NOMBRE_SERVICIO = "RegistroEstudiantes";
    
    public static void main(String[] args) {
        try {
            System.out.println("  SERVIDOR RMI - REGISTRO DE ESTUDIANTES");
            System.out.println();
            
            // Crea la instancia del objeto remoto
            System.out.println("Iniciando implementación del servicio...");
            RegistroImpl registroImpl = new RegistroImpl();
            
            // Crea el registro RMI en el puerto especificado
            System.out.println("► Creando registro RMI en puerto " + PUERTO + "...");
            Registry registry = LocateRegistry.createRegistry(PUERTO);
            
            // Vincula el objeto remoto con un nombre en el registro
            System.out.println("► Registrando servicio como '" + NOMBRE_SERVICIO + "'...");
            registry.rebind(NOMBRE_SERVICIO, registroImpl);
            
            System.out.println();
            System.out.println("SERVIDOR INICIADO EXITOSAMENTE");
            System.out.println("Puerto: " + PUERTO);
            System.out.println("Servicio: " + NOMBRE_SERVICIO);
            System.out.println();
            System.out.println("El servidor está listo para recibir peticiones...");
            System.out.println("Presiona Ctrl+C para detener el servidor.");
            
        } catch (Exception e) {
            System.err.println("Error en el servidor RMI:");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
