package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 8080;
    private static final String HOST = "127.0.0.1";


    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            ClientPrinter clientPrinter = new ClientPrinter(socket);
            clientPrinter.start();
            String userWord;
            while (!socket.isClosed()) {
                Scanner scanner = new Scanner(System.in);
                userWord = scanner.next();
                output.println(userWord);
                output.flush();
                if (userWord.equals("quit")){
                    socket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("PROBLEMS WITH SERVER...");
        }


    }
}
