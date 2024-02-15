import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ClientCallback {

    public GroupChatServer getMessageService() {
        return messageService;
    }

    private GroupChatServer messageService;

    protected ChatClient(GroupChatServer messageService) throws RemoteException {
        super();
        this.messageService = messageService;
        messageService.registerClientCallback(this);
        System.out.println("** WELCOME TO THE CHAT! **\n");
    }

    public void receiveMessage(String sender, String body) throws RemoteException {

        System.out.println("[ " + sender +  " ]: " + body);

    }

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Usage: java ChatService <rmiregistry host>");
                return;
            }

            String host = args[0];

            // Read user input
            Scanner scan = new Scanner(System.in);
            System.out.println("Type your username or type leave to leave the chat");
            String userName = scan.nextLine();  
            
            // Get remote object reference
            Registry registry = LocateRegistry.getRegistry(host);
            GroupChatServer h = (GroupChatServer) registry.lookup("ChatService");
            ChatClient client = new ChatClient(h);

        
            // Remote method invocation
            while(true){
                
                String message = scan.nextLine();  // Read user message
                if(message.equalsIgnoreCase("leave")){
                    h.unregisterClientCallback(client);
                    h.sendMessage(userName, "Leave the chat server");// Send message to other clients
                    System.exit(0); // Exit
                } else {
                    h.sendMessage(userName,message);
                }
                
            }
        

        } catch (Exception e) {
            System.err.println("Error on client: " + e);
            e.printStackTrace();
        }
    }
}
