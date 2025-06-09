import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static controller.Client.HandleClientsController.handleClient;
import static more.ColorfulOutput.*;
import static more.TextEffects.BOLD;

public class Main {

    public static void main(String[] args) throws IOException {
        int port = 9000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            StartupMessageServer.displayStartupMessage(port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                printEstablishConnectionInformation(clientSocket);
                new Thread(() -> handleClient(clientSocket)).start();
            }
        }
    }

    public static void printEstablishConnectionInformation(Socket clientSocket){
        String clientAddress = clientSocket.getInetAddress().getHostAddress();
        int clientPort = clientSocket.getPort();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("\n"+ANSI_BRIGHT_BLACK + "─".repeat(35) + ANSI_RESET);
        System.out.printf("%s%s    ✅ Client connected!%s%n", BOLD, ANSI_GREEN, ANSI_RESET);
        System.out.printf("  IP Address : %s%s%s%n", ANSI_CYAN, clientAddress, ANSI_RESET);
        System.out.printf("  Port       : %s%d%s%n", ANSI_CYAN, clientPort, ANSI_RESET);
        System.out.printf("  Time       : %s%s%s%n", ANSI_CYAN, timestamp, ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK + "─".repeat(35) + ANSI_RESET+"\n\n");
    }
}
