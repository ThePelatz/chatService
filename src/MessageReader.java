import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MessageReader {

    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        while (true) {
            clearScreen(); // Pulisce il terminale prima di stampare i nuovi messaggi
            printMessages(); // Stampa i messaggi dal file JSON
            try {
                TimeUnit.SECONDS.sleep(1); // Attendere 1 secondo prima di leggere il file nuovamente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printMessages() {
        try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {
            Message[] messages = gson.fromJson(reader, Message[].class);
            if (messages != null) {
                for (Message message : messages) {
                    System.out.println("[" + message.getSenderName() + "] : " + message.getBody());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

