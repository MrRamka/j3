package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientPrinter extends Thread{
    private Socket socket;
    private BufferedReader input;

    public ClientPrinter(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                System.out.println("[SERVER] " + input.readLine());
            }
        } catch (IOException e) {
            System.out.println("[SERVER] DISCONNECTED");
        }
    }
}
