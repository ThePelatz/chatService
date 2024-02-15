import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GroupChatServer extends Remote {

void sendMessage(String sender,String body) throws RemoteException;

void registerClientCallback(ClientCallback callback) throws RemoteException;

void unregisterClientCallback(ClientCallback callback) throws RemoteException;


}


    
