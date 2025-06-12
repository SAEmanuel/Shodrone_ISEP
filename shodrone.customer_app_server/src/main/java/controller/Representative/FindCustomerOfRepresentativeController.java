package controller.Representative;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.entity.Costumer;
import domain.valueObjects.NIF;
import network.ObjectDTO;
import persistence.RepositoryProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Optional;

import static more.ColorfulOutput.ANSI_BRIGHT_PURPLE;
import static more.ColorfulOutput.ANSI_RESET;
import static more.TextEffects.BOLD;

/**
 * Server-side controller responsible for retrieving the {@link domain.entity.Costumer}
 * associated with a given Customer Representative.
 * <p>
 * This controller handles the protocol action "FindCustomerOfRepresentative", which expects
 * the client's email and returns the {@link NIF} of the associated customer (if one exists).
 */
public class FindCustomerOfRepresentativeController {

    /**
     * Executes the "FindCustomerOfRepresentative" server action.
     * <p>
     * The expected communication protocol is:
     * <ol>
     *     <li>Read a {@link ObjectDTO}&lt;String&gt; containing the representative's email.</li>
     *     <li>Search for a {@link Costumer} linked to that representative via {@link RepositoryProvider}.</li>
     *     <li>Return a {@link ObjectDTO}&lt;NIF&gt;: the customer's NIF if found, or null otherwise.</li>
     * </ol>
     *
     * @param in    BufferedReader to receive the client's email request.
     * @param out   PrintWriter to send the NIF response back to the client.
     * @param gson  Gson instance used for JSON (de)serialization.
     */
    public static void getCustomerOfRepresentativeAction(BufferedReader in, PrintWriter out, Gson gson) {
        try {
            // Read email from the client
            String receivedEmail = in.readLine();
            Type objectDTOType = new TypeToken<ObjectDTO<String>>() {}.getType();
            ObjectDTO<String> objectDTO = gson.fromJson(receivedEmail, objectDTOType);
            String email = objectDTO.getObject();

            // Try to find a customer linked to this representative
            Optional<Costumer> optionalCustomer = RepositoryProvider.customerRepresentativeRepository().getAssociatedCustomer(email);

            // Prepare response DTO with the customer's NIF or null
            ObjectDTO<NIF> objectDTOToSend = optionalCustomer
                    .map(customer -> ObjectDTO.of(customer.nif()))
                    .orElse(ObjectDTO.of(null));

            // Serialize and send response
            String objectDTOJsonToSend = gson.toJson(objectDTOToSend);
            out.println(objectDTOJsonToSend);

            // Log server-side action
            System.out.printf("%s%s❕SERVER ACTION FINISHED: Get Customer of Representative -> [%s] %s%n",
                    ANSI_BRIGHT_PURPLE, BOLD, email, ANSI_RESET);

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

}
