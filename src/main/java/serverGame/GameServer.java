package serverGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameServer {
    private static final int PORT = 8080;
    private static final String[] WORDS = {"one", "two", "three"};
    public volatile static String currentWord = "";
    private static List<ClientThread> usersList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            startGame();
            while (true) {
                Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(socket);
                clientThread.start();
                clientThread.sendMessage("Hello from server. You must guess correct word");
                usersList.add(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startGame() {
        Random random = new Random();
        currentWord = WORDS[Math.abs(random.nextInt() % (WORDS.length - 1))];
        System.out.println("Current word is \"" + currentWord + "\"");
    }

    public static void foundWinner(String clientThreadName) {
        String messageToUsers = "Winner is " + clientThreadName + ". Word was \"" + currentWord + "\"";
        sendMessageToAllClients(messageToUsers);
        sendMessageToAllClients("Starting new game");
        startGame();
    }

    private static void sendMessageToAllClients(String message) {
        for (ClientThread client : usersList) {
            client.sendMessage(message);
        }
    }
}
