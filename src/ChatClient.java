import java.rmi.*;
import java.rmi.registry.*;
import java.util.List;

public class ChatClient {
    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Usage: java ChatService <rmiregistry host>");
                return;
            }

            String host = args[0];
            
            // Get remote object reference
            Registry registry = LocateRegistry.getRegistry(host);
            Chat h = (Chat) registry.lookup("ChatService");

            // Remote method invocation
            //String res = h.sayHello();

            h.sendMessage("Messaggio 1");
            h.sendMessage("Messaggio 2");

            h.printMessages();

            List<String> history = h.getHistory();

            for (String str : history) {
                System.out.println(str);
            }

            //System.out.println(res);

        } catch (Exception e) {
            System.err.println("Error on client: " + e);
            e.printStackTrace();
        }
    }
}
