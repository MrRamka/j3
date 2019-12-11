package serverGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class ClientThread extends Thread {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            String clientMessage = "";
            while (!clientMessage.equals("quit")){
                clientMessage = input.readLine();
                if (clientMessage.equals(GameServer.currentWord)){
                    GameServer.foundWinner(this.getName());
                }else {
                    output.println("Try another word");
                    output.flush();
                }
            }
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(String message){
        output.println(message);
        output.flush();
    }
}
