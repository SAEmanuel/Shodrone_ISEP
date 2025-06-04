package controller.Client;

import com.google.gson.Gson;
import controller.Representative.FindCustomerOfRepresentativeController;
import domain.entity.User;
import network.UserDTO;
import persistence.AuthenticationRepository;
import persistence.RepositoryProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public class HandleClientsController {

    public static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String received = in.readLine();
            Gson gson = new Gson();
            UserDTO userDTO = gson.fromJson(received, UserDTO.class);

            String email = userDTO.getEmail();
            String password = userDTO.getPassword();

            RepositoryProvider.setUseInMemory(false);
            AuthenticationRepository repo = RepositoryProvider.authenticationRepository();
            Optional<User> userOpt = repo.findCustomerRepresentativeByEmail(email);
            boolean success = userOpt.isPresent() && userOpt.get().passwordMatches(password);

            out.println(success ? "OK" : "FAIL");

            menuOptionsToExecute(in, out, gson,clientSocket);


        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    private static void menuOptionsToExecute(BufferedReader in, PrintWriter out, Gson gson,Socket clientSocket) {
        try {
            String received = in.readLine();

            while (!received.equals("exit")) {

                switch (received) {
                    case "FindCustomerOfRepresentative":
                        FindCustomerOfRepresentativeController.getCustomerOfRepresentativeAction(clientSocket);
                        break;
                    default:
                }

                received = in.readLine();
            }


        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }

    }
}
