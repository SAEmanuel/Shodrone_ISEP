package controller.network;

import com.google.gson.Gson;
import lombok.Getter;
import network.UserDTO;

import static more.ColorfulOutput.*;

import java.io.*;
import java.net.Socket;

/**
 * Controller responsible for handling user authentication
 * and communication with the remote server over a TCP socket.
 * <p>
 * This controller:
 * - Sends login credentials for validation.
 * - Maintains the communication channel for further message exchange.
 * - Manages connection lifecycle (open, send/receive, close).
 */
public class AuthenticationController {

    /** Server IP address (hardcoded for now). */
    @Getter
    private static final String SERVER_HOST = "10.9.23.21";

    /** Server port to connect to. */
    @Getter
    private static final int SERVER_PORT = 9000;

    /** Email of the user who successfully logged in. */
    @Getter
    private static String emailLogin = null;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Attempts to authenticate the user by sending their email and password
     * as a {@link UserDTO} to the remote server.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return {@code true} if login is successful; {@code false} otherwise.
     */
    public boolean doLogin(String email, String password) {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            UserDTO userDTO = new UserDTO(email, password);
            String loginJson = new Gson().toJson(userDTO);
            out.println(loginJson);

            String response = in.readLine();

            if ("OK".equalsIgnoreCase(response)) {
                emailLogin = email;
                return true;
            } else {
                System.out.println(ANSI_YELLOW + "Login falhou: credenciais inválidas." + ANSI_RESET);
                closeConnection();
                return false;
            }

        } catch (IOException e) {
            System.out.println(ANSI_LIGHT_RED + "Erro ao conectar ao servidor: " + SERVER_HOST + ":" + SERVER_PORT + ANSI_RESET);
            return false;
        }
    }

    /**
     * Sends a plain text message to the connected server.
     *
     * @param message The message string to send.
     */
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    /**
     * Receives a message from the server.
     *
     * @return The message received as a string, or {@code null} if no input.
     * @throws IOException If an error occurs during input reading.
     */
    public String receiveMessage() throws IOException {
        if (in != null) {
            return in.readLine();
        }
        return null;
    }

    /**
     * Closes the socket connection and associated input/output streams.
     * Ensures that the network resources are properly released.
     */
    public void closeConnection() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.out.println(ANSI_LIGHT_RED + "Erro ao fechar a ligação." + ANSI_RESET);
        }
    }
}
