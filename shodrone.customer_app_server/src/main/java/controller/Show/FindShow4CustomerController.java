package controller.Show;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.entity.Costumer;
import domain.entity.Show;
import domain.valueObjects.NIF;
import network.ObjectDTO;
import network.ShowDTO;
import persistence.RepositoryProvider;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static more.ColorfulOutput.ANSI_BRIGHT_PURPLE;
import static more.ColorfulOutput.ANSI_RESET;
import static more.TextEffects.BOLD;

/**
 * Controller class responsible for handling the server-side logic
 * for retrieving all {@link Show} entities associated with a given {@link Costumer}.
 * <p>
 * This controller is triggered by a remote client request and uses the
 * {@link RepositoryProvider} to access the persistent storage.
 * </p>
 * <p>
 * The result is returned in the form of {@link ShowDTO} objects
 * serialized with Gson and sent over the network stream.
 * </p>
 */
public class FindShow4CustomerController {

    /**
     * Static method that executes the server-side action for the "FindShow4Customer" protocol.
     * <p>
     * The method follows this sequence:
     * <ol>
     *     <li>Reads a {@code NIF} (wrapped in {@code ObjectDTO}) sent by the client.</li>
     *     <li>Validates whether the customer exists in the repository using that NIF.</li>
     *     <li>If the customer exists, retrieves all their associated shows.</li>
     *     <li>Returns the number of shows and each show serialized as {@code ShowDTO}.</li>
     *     <li>If no shows exist, returns 0. If customer doesn't exist, returns -1.</li>
     * </ol>
     * </p>
     *
     * @param in   Input stream from the client (to read the NIF).
     * @param out  Output stream to the client (to send show data).
     * @param gson Gson instance used for serialization/deserialization.
     */
    public static void getShow4CustomerAction(BufferedReader in, PrintWriter out, Gson gson) {
        try {
            // Step 1: Receive NIF from client
            String receivedEmail = in.readLine();
            Type objectDTOType = new TypeToken<ObjectDTO<NIF>>() {}.getType();
            ObjectDTO<NIF> objectDTO = gson.fromJson(receivedEmail, objectDTOType);
            NIF nifCustomer = objectDTO.getObject();

            // Step 2: Find customer by NIF
            Optional<Costumer> customerToAssociatedNIF = RepositoryProvider.costumerRepository().findByNIF(nifCustomer);

            if (customerToAssociatedNIF.isPresent()) {
                // Step 3: Find shows for that customer
                Optional<List<Show>> listOfShowsOptional = RepositoryProvider.showRepository().findByCostumer(customerToAssociatedNIF.get());

                if (listOfShowsOptional.isPresent()) {
                    List<Show> listOfShows = listOfShowsOptional.get();
                    int numShows = listOfShows.size();

                    // Step 4: Send number of shows
                    ObjectDTO<Integer> objectDTOToSendInformative = ObjectDTO.of(numShows);
                    String objectDTOJsonToSendInformative = gson.toJson(objectDTOToSendInformative);
                    out.println(objectDTOJsonToSendInformative);

                    // Step 5: Send each show as ShowDTO
                    for (Show show : listOfShows) {
                        ShowDTO objectDTOToSend = ShowDTO.fromEntity(show);
                        String objectDTOJsonToSend = gson.toJson(objectDTOToSend);
                        out.println(objectDTOJsonToSend);
                    }
                } else {
                    // No shows for this customer
                    ObjectDTO<Integer> objectDTOToSendInformative = ObjectDTO.of(0);
                    out.println(gson.toJson(objectDTOToSendInformative));
                }
            } else {
                // Customer not found
                ObjectDTO<Integer> objectDTOToSendInformative = ObjectDTO.of(-1);
                out.println(gson.toJson(objectDTOToSendInformative));
            }

            // Log successful server response
            System.out.printf("%s%s❕SERVER ACTION FINISHED: Get Show for Customer of Representative -> [%s] %s%n",
                    ANSI_BRIGHT_PURPLE, BOLD, nifCustomer, ANSI_RESET);

        } catch (IOException e) {
            Utils.printFailMessage("Client error: " + e.getMessage());
        }
    }
}
