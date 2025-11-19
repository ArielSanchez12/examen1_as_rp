import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Registro extends Remote {
    
    String registrarEstudiante(String nombre, String carrera, int semestre, String correo) throws RemoteException;
    String buscarEstudiantePorID(String id) throws RemoteException;
}
