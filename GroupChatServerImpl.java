import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GroupChatServerImpl implements GroupChatServer {

    private List<ClientCallback> clients;
    private String fileName = "chatHistory.txt";

    public GroupChatServerImpl() throws RemoteException {
        super();
        clients = new ArrayList<>();
    }


    public void sendMessage(String sender, String body) throws RemoteException {
        
        //saves the message in the history file
        saveMessage(sender, body);

        ClientCallback unsub = null;

        try{
            for (ClientCallback client : clients) {
                
                unsub = client;
                //the server forwards the message to all the clients, even to the sender of that message
                client.receiveMessage(sender, body);

            }

        } catch (java.rmi.RemoteException e) {
            unregisterClientCallback(unsub);
            System.out.println("Someone has left the chat");
        }

    }

    public List<String> printHistory(){
        File file = new File(fileName);
        List<String> messages = new ArrayList<>();
        
        // check if the file exists, if not, means that the history is emtpy
        if (!file.exists() || !file.isFile()) {
            return messages;
        }
        
        try {

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String message;
            
            while ((message = reader.readLine()) != null) {
                messages.add(message);
            }
            
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error while reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return messages;

    }

    public void saveMessage(String sender, String body) {
        try {
        
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName  , true));
            
            writer.write("[ " + sender +  " ]: " + body);
            writer.newLine();
            
            writer.close();

        } catch (IOException e) {
            System.err.println("Error while saving the message in history: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void registerClientCallback(ClientCallback callback) throws RemoteException {
        clients.add(callback);
    }

    
    public void unregisterClientCallback(ClientCallback callback) throws RemoteException {
        clients.remove(callback);
    }

}
