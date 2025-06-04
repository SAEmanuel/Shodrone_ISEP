import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static controller.Client.HandleClientsController.handleClient;

public class Main {

    public static void main(String[] args) throws IOException {
        int port = 9000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Customer App Server running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        }
    }
}
