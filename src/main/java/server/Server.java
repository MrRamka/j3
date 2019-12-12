package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final int PORT = 8080;
    private static List<ClientHandler> clientHandlerList = new ArrayList<>();
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started");
            Game game = new Game();
            while (true) {
                Socket userSocket = serverSocket.accept();
                ClientHandler user = new ClientHandler(userSocket);
                user.start();
                System.out.println("Connected from: " + userSocket.getLocalAddress());
                clientHandlerList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageToAllClients(String message){
        for (ClientHandler client : clientHandlerList) {
            client.sendMessage(message);
        }

    }

}
