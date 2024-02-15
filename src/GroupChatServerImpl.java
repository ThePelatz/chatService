import java.rmi.*;
import java.util.ArrayList;
import java.util.List;

public class GroupChatServerImpl implements GroupChatServer {

    private List<ClientCallback> clients;

    public GroupChatServerImpl() throws RemoteException {
        super();
        clients = new ArrayList<>();
    }


    public void sendMessage(String sender, String body) throws RemoteException {
        
        for (ClientCallback client : clients) {
            client.receiveMessage(sender, body);
        }
    }

    //to be implemented, should print all the previous messages right after connection
    public void printMessages() {

    }

    public void registerClientCallback(ClientCallback callback) throws RemoteException {
        clients.add(callback);
    }

    
    public void unregisterClientCallback(ClientCallback callback) throws RemoteException {
        clients.remove(callback);
    } 

}
