package controller.Client;

import com.google.gson.Gson;
import controller.Representative.FindCustomerOfRepresentativeController;
import controller.Show.FindShow4CustomerController;
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

            menuOptionsToExecute(in, out, gson);


        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    private static void menuOptionsToExecute(BufferedReader in, PrintWriter out, Gson gson) {
        try {
            System.out.println("SERVER: Waiting for option to be selected...");
            String received = in.readLine();

            while (received != null && !received.equals("exit")) {
                System.out.println("Recebido: " + received);

                switch (received) {
                    case "FindCustomerOfRepresentative":
                        System.out.println("Chamando getCustomerOfRepresentativeAction");
                        FindCustomerOfRepresentativeController.getCustomerOfRepresentativeAction(in, out, gson);
                        break;
                    case "FindShow4Customer":
                        System.out.println("Chamando getShow4CustomerAction");
                        FindShow4CustomerController.getShow4CustomerAction(in, out, gson);
                        break;
                    default:
                        System.out.println("Comando não reconhecido: " + received);
                }

                System.out.println("SERVER: Waiting for option to be selected...");
                received = in.readLine();
            }

            System.out.println("Conexão encerrada ou comando 'exit' recebido.");

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

}
