package controller.network;

import com.google.gson.Gson;
import lombok.Getter;
import network.UserDTO;

import static more.ColorfulOutput.*;

import java.io.*;
import java.net.Socket;

public class AuthenticationController {

    @Getter
    private static final String SERVER_HOST = "10.9.23.21";
    @Getter
    private static final int SERVER_PORT = 9000;
    @Getter
    private static String emailLogin = null;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;


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

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public String receiveMessage() throws IOException {
        if (in != null) {
            return in.readLine();
        }
        return null;
    }

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
