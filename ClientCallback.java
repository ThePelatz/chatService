import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {
    public void receiveMessage(String sender, String body) throws RemoteException;
}