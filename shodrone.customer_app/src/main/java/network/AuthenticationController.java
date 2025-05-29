package network;

import com.google.gson.Gson;
import network.LoginRequest;

import java.io.*;
import java.net.Socket;

public class AuthenticationController {

    private static final String SERVER_HOST = "localhost"; //todo IP do servidor MUDAR!!!!
    private static final int SERVER_PORT = 9000;


    public boolean doLogin(String email, String password) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            LoginRequest loginRequest = new LoginRequest(email, password);
            Gson gson = new Gson();
            String loginJson = gson.toJson(loginRequest);

            out.println(loginJson);

            String response = in.readLine();
            return "OK".equalsIgnoreCase(response);

        } catch (IOException e) {
            System.out.println("Login failed: " + e.getMessage());
            return false;
        }
    }
}