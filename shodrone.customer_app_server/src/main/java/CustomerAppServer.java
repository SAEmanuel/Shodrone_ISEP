import com.google.gson.Gson;
import network.LoginRequest;
import persistence.RepositoryProvider;
import persistence.AuthenticationRepository;
import domain.entity.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class CustomerAppServer {

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

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String received = in.readLine();
            Gson gson = new Gson();
            LoginRequest loginRequest = gson.fromJson(received, LoginRequest.class);

            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            AuthenticationRepository repo = RepositoryProvider.authenticationRepository();
            Optional<User> userOpt = repo.findCustomerRepresentativeByEmail(email);
            boolean success = userOpt.isPresent() && userOpt.get().passwordMatches(password);

            out.println(success ? "OK" : "FAIL");

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
