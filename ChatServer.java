import java.rmi.server.*;
import java.rmi.registry.*;

public class ChatServer {

    public static void main(String[] args) {

        try {
            GroupChatServerImpl h = new GroupChatServerImpl();
            GroupChatServer h_stub = (GroupChatServer) UnicastRemoteObject.exportObject(h, 0);

            // Register the remote object in RMI registry with a given identifier
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("ChatService", h_stub);

            System.out.println("Server ready");

        } catch (Exception e) {
            System.err.println("Error on server: " + e);
            e.printStackTrace();
        }
    }
}
