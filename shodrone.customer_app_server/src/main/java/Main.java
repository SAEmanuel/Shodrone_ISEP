import controller.Client.HandleClientsController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static controller.Client.HandleClientsController.handleClient;
import static more.ColorfulOutput.*;
import static more.TextEffects.BOLD;

/**
 * Entry point for the server-side application.
 * <p>
 * This class initializes a TCP server on a predefined port (default 9000) and listens for incoming client connections.
 * For each new connection, a dedicated thread is spawned to handle the client using {@link HandleClientsController#handleClient(Socket)}.
 */
public class Main {

    /**
     * Starts the server on port 9000.
     * <p>
     * - Creates a {@link ServerSocket} listening on port 9000.
     * - Accepts incoming client connections in an infinite loop.
     * - For each client, logs the connection info and spawns a new thread to handle the session.
     *
     * @param args not used.
     * @throws IOException if the server socket fails to bind or accept connections.
     */
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

    /**
     * Logs the connection information of a newly connected client.
     *
     * @param clientSocket The {@link Socket} of the connected client.
     */
    public static void printEstablishConnectionInformation(Socket clientSocket){
        String clientAddress = clientSocket.getInetAddress().getHostAddress();
        int clientPort = clientSocket.getPort();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("\n" + ANSI_BRIGHT_BLACK + "─".repeat(35) + ANSI_RESET);
        System.out.printf("%s%s    ✅ Client connected!%s%n", BOLD, ANSI_GREEN, ANSI_RESET);
        System.out.printf("  IP Address : %s%s%s%n", ANSI_CYAN, clientAddress, ANSI_RESET);
        System.out.printf("  Port       : %s%d%s%n", ANSI_CYAN, clientPort, ANSI_RESET);
        System.out.printf("  Time       : %s%s%s%n", ANSI_CYAN, timestamp, ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK + "─".repeat(35) + ANSI_RESET + "\n\n");
    }
}
