import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;

public class MessageSaver {

    private static final Gson gson = new Gson();

    public static void saveMessagesToJson(String fileName, Message[] messages) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            gson.toJson(messages, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Esempio di utilizzo
        Message[] messages = {
            new Message("Alice", "Ciao, come stai?"),
            new Message("Bob", "Tutto bene, grazie! E tu?")
        };

        saveMessagesToJson("messages.json", messages);
    }
}
