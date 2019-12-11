package serverHm;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Server {
    private static final int PORT = 8080;
    private static final String CONTENT_HEADER = "Content-Length: ";
    private static final String IP = "/127.0.0.1";
    private static final String FILE_PATH = "D:\\MAVEN_PROJECTS\\j3\\src\\main\\java\\serverHm\\users.csv";

    public static void main(String[] args) {
        String path = "";
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                boolean isNormalPath = true;
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter output = new PrintWriter(socket.getOutputStream());
                String inputLine = input.readLine();
                System.out.println(inputLine);
                String[] firstLineData = inputLine.split(" ");
                if (!firstLineData[1].equals("/")){
                    isNormalPath = false;
                }
                boolean isPost = inputLine.startsWith("POST");
                int contentLength = 0;

                while (!(inputLine = input.readLine()).equals("")) {
                    if (isPost && inputLine.startsWith(CONTENT_HEADER)) {
                        contentLength = Integer.parseInt(inputLine.substring(CONTENT_HEADER.length()));
                    }
                }
                StringBuilder body = new StringBuilder();
                if (isPost) {
                    int c;
                    for (int i = 0; i < contentLength; i++) {
                        c = input.read();
                        body.append((char) c);
                    }
                    String[] data = body.toString().split("&");
                    System.out.println(body.toString());
                    String name = data[0].split("=")[1];
                    String date = data[1].split("=")[1];
                    write(name, date);
                }
                if (!socket.getInetAddress().toString().equals(IP)){
                    System.out.println("Test");
                }
                if (!isNormalPath){
                    output.println("HTTP/1.1 404 Not found");
                    output.println("Content-Type: text/html; charset=utf-8");
                    output.println();
                    output.println("<h1>Bad request</h1>");
                }else {
                    output.println("HTTP/1.1 200 OK");
                    output.println("Content-Type: text/html; charset=utf-8");
                    output.println();
                    output.println(getUsers());
                    output.println(getForm());
                }
                output.flush();
                output.close();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private static String getForm() {
        return "<p>Add</p><form method='POST' action='/'>\n" +
                "<input required name='name' type='text'/>\n" +
                "<input required name='date' type='date'/>\n" +
                "<input type='submit'/>\n" +
                "</form>\n";
    }


    private static String getUsers() throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(new File(FILE_PATH));
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            stringBuilder.append("<p> * Name: ").append(data[0]);
            stringBuilder.append("; Birth date: ").append(data[1]).append("</p>");
        }
        return stringBuilder.toString();
    }

    private static void write(String name, String date) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(FILE_PATH), true);
        fileWriter.write(name + "," + date + "\n");
        fileWriter.flush();
        fileWriter.close();
    }
}

