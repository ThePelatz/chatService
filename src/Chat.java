
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Chat extends Remote {
    // A method provided by the
    // remote object
public String sayHello() throws RemoteException;

public void sendMessage(String mess) throws RemoteException;

public void printMessages() throws RemoteException;

public List<String> getHistory() throws RemoteException;

}


    
