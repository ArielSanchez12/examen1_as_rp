import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClienteRMI {
    
    private static String HOST = "localhost";
    private static int PUERTO = 1099;
    private static String NOMBRE_SERVICIO = "RegistroEstudiantes";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Conectando al servidor en " + HOST + ":" + PUERTO);
            Registry registry = LocateRegistry.getRegistry(HOST, PUERTO);
            Registro registro = (Registro) registry.lookup(NOMBRE_SERVICIO);
            System.out.println();
            
            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                String opcionStr = scanner.nextLine();
                int opcion;
                try {
                    opcion = Integer.parseInt(opcionStr);
                } catch (NumberFormatException e) {
                    System.out.println("\nIngresa una opcion valida\n");
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
                        System.out.println("Te has desconectado del servidor");
                        continuar = false;
                        break;
                        
                    default:
                        System.out.println("Ingresa una opcion valida\n");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    private static void mostrarMenu() {
        System.out.println("Elija una opcion:");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Buscar estudiante por ID");
        System.out.println("3. Salir");
        System.out.print("Selecciona una opcion: ");
    }
    
    // Registra un nuevo estudiante
    private static void registrarEstudiante(Scanner scanner, Registro registro) {
        try {
            System.out.print("Ingresa tu nombre: ");
            String nombre = scanner.nextLine();
            
            if (nombre == null) {
                System.out.println("\nIngresa un nombre valido\n");
                return;
            }
            
            System.out.print("Ingresa tu carrera: ");
            String carrera = scanner.nextLine();
            
            if (carrera == null) {
                System.out.println("\nIngresa una carrera valida\n");
                return;
            }
            
            System.out.print("Ingresa en que semestres estas (1-5): ");
            String semestreStr = scanner.nextLine();
            int semestre;
            
            try {
                semestre = Integer.parseInt(semestreStr);
            } catch (NumberFormatException e) {
                System.out.println("\nIngresa un semestre valido (nbumero del 1 al 5)\n");
                return;
            }
            
            // Solicita correo
            System.out.print("Ingresa tu correo: ");
            String correo = scanner.nextLine();
            
            if (correo == null) {
                System.out.println("\nIngresa un correo valido (por ejemplo distribuidas@gmail.com)\n");
                return;
            }
            String respuesta = registro.registrarEstudiante(nombre, carrera, semestre, correo);
            System.out.println();
            System.out.println(respuesta);
            System.out.println();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
        }
    }
    
    private static void buscarEstudiante(Scanner scanner, Registro registro) {
        try {
            System.out.print("Ingresa el ID del estudiante: ");
            String id = scanner.nextLine();
            
            if (id == null) {
                System.out.println("\nIngresa un ID valido\n");
                return;
            }
            
            System.out.println("\nBuscando en el servidor...");
            String respuesta = registro.buscarEstudiantePorID(id);
            
            System.out.println();
            System.out.println(respuesta);
            System.out.println();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
        }
    }
}
