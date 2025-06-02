package network;

import com.google.gson.Gson;
import static more.ColorfulOutput.*;

import java.io.*;
import java.net.Socket;

public class AuthenticationController {

    private static final String SERVER_HOST = "10.8.0.80"; //"localhost" for local pc
    private static final int SERVER_PORT = 9000;


    public boolean doLogin(String email, String password) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            UserDTO userDTO = new UserDTO(email, password);
            Gson gson = new Gson();
            String loginJson = gson.toJson(userDTO);

            out.println(loginJson);

            String response = in.readLine();
            return "OK".equalsIgnoreCase(response);

        } catch (IOException e) {
            System.out.println(ANSI_LIGHT_RED + "Servers are down! Login unavailable..." + ANSI_RESET);
            System.exit(1);
            return false;
        }
    }

}