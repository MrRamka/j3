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
    private static final String HTTP = "HTTP/1.1";
    private static final String CODE_200 = "200 OK";
    private static final String CODE_400 = "404 Not found";
    private static final String CODE_404 = "405 Method Not Allowed";
    private static final String CODE_500 = "500 Internal Server Error";
    private static final String CODE_505 = "505 HTTP Version Not Supported";


    public static void main(String[] args) {
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
                boolean isGet = inputLine.startsWith("GET");
                boolean isHTTP = true;
                boolean isPost = inputLine.startsWith("POST");
                int contentLength = 0;
                if (!firstLineData[1].equals("/")) {
                    isNormalPath = false;
                }
                if (!firstLineData[2].equals(HTTP)) {
                    isHTTP = false;
                }

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

                String statusCode = CODE_200;

                if (!isNormalPath) {
                    statusCode = CODE_400;
                } else if (!(isGet || isPost)) {
                    statusCode = CODE_404;
                } else if (!isHTTP) {
                    statusCode = CODE_505;
                }
                String users = "";
                try {
                    users = getUsers();
                } catch (FileNotFoundException ex) {
                    statusCode = CODE_500;
                }
                if (!statusCode.equals(CODE_200)) {
                    output.println("HTTP/1.1 " + statusCode);
                    output.println("Content-Type: text/html; charset=utf-8");
                    output.println();
                    output.println("<h1>" + statusCode + "</h1>");
                } else {
                    output.println("HTTP/1.1 " + statusCode);
                    output.println("Content-Type: text/html; charset=utf-8");
                    output.println();
                    output.println(users);
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

