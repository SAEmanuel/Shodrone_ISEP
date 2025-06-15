import handler.HandleSimulation;
import utils.StartupMessageServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 9090;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            StartupMessageServer.displayStartupMessage(port, "Testing App");

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> HandleSimulation.handle(socket)).start();
            }
        }
    }
}