import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

// Cliente RMI para el sistema de registro de estudiantes
public class ClienteRMI {
    
    private static final String HOST = "localhost";
    private static final int PUERTO = 1099;
    private static final String NOMBRE_SERVICIO = "RegistroEstudiantes";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Conecta al registro RMI
            System.out.println("  CLIENTE RMI - REGISTRO DE ESTUDIANTES");
            System.out.println("Conectando al servidor en " + HOST + ":" + PUERTO + "...");
            
            Registry registry = LocateRegistry.getRegistry(HOST, PUERTO);
            Registro registro = (Registro) registry.lookup(NOMBRE_SERVICIO);
            
            System.out.println("Conectado exitosamente al servidor.");
            System.out.println();
            
            boolean continuar = true;
            
            while (continuar) {
                mostrarMenu();
                
                String opcionStr = scanner.nextLine().trim();
                
                // Valida que la opción sea un número
                int opcion;
                try {
                    opcion = Integer.parseInt(opcionStr);
                } catch (NumberFormatException e) {
                    System.out.println("\nError: Ingresa un número válido (1, 2 o 3).\n");
                    continue;
                }
                
                System.out.println();
                
                switch (opcion) {
                    case 1:
                        registrarEstudiante(scanner, registro);
                        break;
                        
                    case 2:
                        buscarEstudiante(scanner, registro);
                        break;
                        
                    case 3:
                        System.out.println("  SALIENDO DEL SISTEMA");
                        System.out.println("¡Gracias por usar el sistema de registro!");
                        continuar = false;
                        break;
                        
                    default:
                        System.out.println("Opción inválida. Por favor, elige 1, 2 o 3.\n");
                }
            }
            
        } catch (Exception e) {
            System.err.println("\nError en el cliente RMI:");
            System.err.println(e.getMessage());
            System.err.println("\nAsegúrate de que el servidor esté ejecutándose.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    // Muestra el menú principal
    private static void mostrarMenu() {
        System.out.println("MENÚ PRINCIPAL");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Buscar estudiante por ID");
        System.out.println("3. Salir");
        System.out.print("Selecciona una opción: ");
    }
    
    // Registra un nuevo estudiante
    private static void registrarEstudiante(Scanner scanner, Registro registro) {
        try {
            System.out.println("REGISTRO DE ESTUDIANTE");
            
            // Solicita nombre
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine().trim();
            
            if (nombre.isEmpty()) {
                System.out.println("\nError: El nombre no puede estar vacío.\n");
                return;
            }
            
            // Solicita carrera
            System.out.print("Carrera: ");
            String carrera = scanner.nextLine().trim();
            
            if (carrera.isEmpty()) {
                System.out.println("\nError: La carrera no puede estar vacía.\n");
                return;
            }
            
            // Solicita semestre
            System.out.print("Semestre (1-12): ");
            String semestreStr = scanner.nextLine().trim();
            int semestre;
            
            try {
                semestre = Integer.parseInt(semestreStr);
            } catch (NumberFormatException e) {
                System.out.println("\nError: El semestre debe ser un número.\n");
                return;
            }
            
            // Solicita correo
            System.out.print("Correo electrónico: ");
            String correo = scanner.nextLine().trim();
            
            if (correo.isEmpty()) {
                System.out.println("\nError: El correo no puede estar vacío.\n");
                return;
            }
            
            // Llama al método remoto
            System.out.println("\nEnviando datos al servidor...");
            String respuesta = registro.registrarEstudiante(nombre, carrera, semestre, correo);
            
            System.out.println();
            System.out.println(respuesta);
            System.out.println();
            
        } catch (Exception e) {
            System.err.println("\n✗ Error al registrar estudiante:");
            System.err.println(e.getMessage());
            System.out.println();
        }
    }
    
    // Busca un estudiante por ID
    private static void buscarEstudiante(Scanner scanner, Registro registro) {
        try {
            System.out.println("         BÚSQUEDA DE ESTUDIANTE");
            
            System.out.print("Ingresa el ID del estudiante: ");
            String id = scanner.nextLine().trim();
            
            if (id.isEmpty()) {
                System.out.println("\nError: El ID no puede estar vacío.\n");
                return;
            }
            
            // Llama al método remoto
            System.out.println("\nBuscando en el servidor...");
            String respuesta = registro.buscarEstudiantePorID(id);
            
            System.out.println();
            System.out.println(respuesta);
            System.out.println();
            
        } catch (Exception e) {
            System.err.println("\nError al buscar estudiante:");
            System.err.println(e.getMessage());
            System.out.println();
        }
    }
}
