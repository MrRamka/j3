package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader clientReader;
    private PrintWriter clientWriter;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        this.clientWriter = new PrintWriter(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            String clientMessage = "";
            while (!clientMessage.equals("quit")) {
                sendMessage("Enter your word");
                clientMessage = clientReader.readLine();
                if (clientMessage.equals("quit")) {
                    clientSocket.close();
                    break;
                }
                if (Game.isCorrectWord(clientMessage)) {
                    String message = " Winner is " + this.getName() + ". Word was \"" + clientMessage + "\"";
                    System.out.println(message);
                    Server.sendMessageToAllClients(message);
                    Server.sendMessageToAllClients("Starting new game");
                } else {
                    sendMessage("Miss. Try another word");
                }
            }
            clientReader.close();
            clientWriter.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        clientWriter.println(message);
        clientWriter.flush();
    }
}
