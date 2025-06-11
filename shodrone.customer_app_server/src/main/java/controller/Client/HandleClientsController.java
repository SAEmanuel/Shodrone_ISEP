package controller.Client;

import com.google.gson.Gson;
import controller.Representative.FindCustomerOfRepresentativeController;
import controller.Show.FindShow4CustomerController;
import controller.proposals.FindCustomersProposalsAction;
import controller.proposals.AnalyseProposalResponse;
import domain.entity.User;
import network.UserDTO;
import persistence.AuthenticationRepository;
import persistence.RepositoryProvider;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

import static more.ColorfulOutput.ANSI_MAROON;
import static more.ColorfulOutput.ANSI_RESET;
import static more.TextEffects.BOLD;

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
            Utils.printFailMessage("❌ Client error: " + e.getMessage());
        }
    }

    private static void menuOptionsToExecute(BufferedReader in, PrintWriter out, Gson gson) {
        try {
            System.out.printf("%s🛜 [SERVER] : Waiting for option to be selected...%s%n",BOLD,ANSI_RESET);
            String received = in.readLine();

            while (received != null && !received.equals("exit")) {
                System.out.printf("%s%s📡 [SERVER RECEIVED] : %s%s%n",BOLD,ANSI_MAROON,received,ANSI_RESET);

                switch (received) {
                    case "FindCustomerOfRepresentative":
                        System.out.println("📍 Calling getCustomerOfRepresentativeAction");
                        FindCustomerOfRepresentativeController.getCustomerOfRepresentativeAction(in, out, gson);
                        break;
                    case "FindShow4Customer":
                        System.out.println("📍 Calling getShow4CustomerAction");
                        FindShow4CustomerController.getShow4CustomerAction(in, out, gson);
                        break;

                    case "FindCustomersProposals":
                        System.out.println("📍 Calling findCustomersProposalsAction");
                        FindCustomersProposalsAction.findCustomersProposalsAction(in, out, gson);
                        break;

                    case "AnalyseProposalResponse":
                        System.out.println("📍 Calling analyseProposalResponseAction");
                        String proposalJson = in.readLine();
                        AnalyseProposalResponse.analyseProposalResponseAction(proposalJson, out, gson);
                        break;

                    default:
                        Utils.printAlterMessage("❌ Command not found: " + received);
                }

                System.out.printf("%s🛜 [SERVER] : Waiting for option to be selected...%s%n",BOLD,ANSI_RESET);
                received = in.readLine();
            }

            System.out.printf("%s🛜 SERVER: Connection close or command 'exit' received.%s%n",BOLD,ANSI_RESET);

        } catch (IOException e) {
            System.out.println("❌ Client error: " + e.getMessage());
        }
    }

}
