import java.rmi.*;
import java.util.ArrayList;
import java.util.List;

public class ChatImpl implements Chat {

    private String message;
    private List<String> listaStringhe = new ArrayList<>();

    public ChatImpl(String s) {
        message = s;
    }

    public String sayHello() throws RemoteException {
        return message;
    }
    
    public void sendMessage(String mess) {
        listaStringhe.add(mess);
        System.out.println("Stringa \"" + mess + "\" inserita nella lista.");
    }

    public void printMessages() {
        System.out.println("View previous messages:");
        for (String stringa : listaStringhe) {
            System.out.println(stringa);
        }
    }

    public List<String> getHistory() {
        return listaStringhe;
    }
}
