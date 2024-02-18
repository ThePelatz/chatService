import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
public class ChatGraphic  {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField messageField;
    private JTextField nameField;
    private JButton sendButton;
    private JButton nameButton;
    private JButton leaveButton;
    private ChatClient client;
    private List<String> messages;

    
    public ChatGraphic() throws RemoteException, NotBoundException {
        frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        Registry registry = LocateRegistry.getRegistry("localhost");
        GroupChatServer h = (GroupChatServer) registry.lookup("ChatService");
        this.client = new ChatClient(h);
        //panel for displaying all messages
        this.messages = new ArrayList<>();
        this.messages = client.getHistoryMessages();
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        //displays all history messages
        for(String message : this.messages) {
            chatArea.append(message + "\n");    
        }
       
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        
        //panel for taking the name of user
        JPanel namePanel = new JPanel(new BorderLayout());
        nameField = new JTextField();
        namePanel.add(nameField, BorderLayout.CENTER);

        nameButton = new JButton("Send the name");
        nameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    sendName();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        namePanel.add(nameButton, BorderLayout.EAST);

        //panel for taking the message for sending
        JPanel messagePanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        messagePanel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    sendMessage();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        messagePanel.add(sendButton, BorderLayout.EAST);

        bottomPanel.add(namePanel, BorderLayout.NORTH);
        bottomPanel.add(messagePanel, BorderLayout.CENTER);
        //bottom for leaving the chat window
        leaveButton = new JButton("Leave Chat");
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                leaveChat();
            }
        });
        bottomPanel.add(leaveButton, BorderLayout.SOUTH);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        int delay = 1000; // Intervalle en millisecondes (1 seconde)
        new Timer(delay, e -> {
            List<String> newMessages = client.getNewMessages();
            SwingUtilities.invokeLater(() -> { // Assurez-vous que la mise à jour de l'UI se fait sur l'EDT
                for (String message : newMessages) {
                    chatArea.append(message + "\n");
                }
                if (!newMessages.isEmpty()) {
                    chatArea.setCaretPosition(chatArea.getDocument().getLength()); // Fait défiler vers le bas si de nouveaux messages sont ajoutés
                }
                chatArea.repaint();
            });
        }).start();
        
        frame.setVisible(true);
        
    }

    private void sendMessage() throws RemoteException {
        String message = messageField.getText();
        chatArea.append("You: " + message + "\n");
        messageField.setText("");
        client.sendMessage(message);
    }
    private void sendName() throws RemoteException {
        String name = nameField.getText();
        nameField.setText("");
        client.setUsername(name);
    }

    private void leaveChat() {
        client.leaveChat();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ChatGraphic();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

