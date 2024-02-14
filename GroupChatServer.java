import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GroupChatServer extends Remote {

void sendMessage(String sender,String body) throws RemoteException;

List<String> printHistory() throws RemoteException;

void registerClientCallback(ClientCallback callback) throws RemoteException;

//void unregisterClientCallback(ClientCallback callback) throws RemoteException;


}


    
