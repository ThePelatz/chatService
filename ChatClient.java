import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class ChatClient extends UnicastRemoteObject implements ClientCallback {

    private GroupChatServer messageService;
    private String userName;   
    private List<String> newMessages = new ArrayList<String>();;

    protected ChatClient(GroupChatServer messageService) throws RemoteException {
        super();
            this.messageService = messageService;
            this.messageService.registerClientCallback(this);
            messageService.registerClientCallback(this);
        
    }

    public void leaveChat() {
        System.exit(0);
    }
    public List<String> getHistoryMessages()throws RemoteException{
        List<String> historyMessages = messageService.printHistory();

        if (historyMessages.isEmpty()) {
            historyMessages.add("No messages sent yet");
        } else {
            historyMessages.add("\n--------** MESSAGE HYSTORY**-------\n");
        }
        return historyMessages;
    }

    public void sendMessage(String body) throws RemoteException {
        messageService.sendMessage(this.userName, body);
    }

    public void setUsername(String username) throws RemoteException {
        this.userName = username;   
    }

    public void receiveMessage(String sender, String body) throws RemoteException {
        
        newMessages.add("[ " + sender +  " ]: " + body) ;

    }

    public List<String> getNewMessages() {
        return newMessages;
    }

    public static void main(String[] args) {
        
    }
}
