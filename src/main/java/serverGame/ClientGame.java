package serverGame;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientGame {
    private static final int PORT = 8080;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            ClientPrintThread clientPrintThread = new ClientPrintThread(socket);
            clientPrintThread.start();
            String message;
            while (!socket.isClosed()){
                System.out.println("Enter you word: ");
                Scanner scanner = new Scanner(System.in);
                message = scanner.next();
                output.println(message);
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
