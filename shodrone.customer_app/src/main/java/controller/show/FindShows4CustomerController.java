package controller.show;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.network.AuthenticationController;
import domain.valueObjects.NIF;
import network.ObjectDTO;
import network.ShowDTO;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for retrieving the list of shows associated with a given customer,
 * identified by their {@link NIF}, by communicating with a remote server.
 *
 * <p>This controller serializes the {@code NIF} into a JSON request, sends it through the
 * {@link AuthenticationController}'s socket-based connection, and parses the received list of
 * {@link ShowDTO} objects.</p>
 *
 * <p>It is typically used in the context of a customer representative checking scheduled shows for their client.</p>
 */
public class FindShows4CustomerController {

    private final AuthenticationController authController;

    /**
     * Constructs the controller with the specified authentication controller,
     * which manages the socket connection and session context.
     *
     * @param authController The controller handling authentication and communication.
     */
    public FindShows4CustomerController(AuthenticationController authController){
        this.authController = authController;
    }

    /**
     * Retrieves a list of {@link ShowDTO} objects representing shows scheduled for the customer with the given NIF.
     * Communicates with the server using a two-step protocol:
     * <ol>
     *     <li>Sends a command message: {@code "FindShow4Customer"}.</li>
     *     <li>Sends the customer {@link NIF} wrapped in an {@link ObjectDTO} as JSON.</li>
     *     <li>Receives an integer indicating the number of shows, or:
     *         <ul>
     *             <li>{@code -1} if the customer was not found.</li>
     *             <li>{@code 0} if the customer has no shows.</li>
     *         </ul>
     *     </li>
     *     <li>If shows exist, receives that number of {@link ShowDTO} messages and stores them in a list.</li>
     * </ol>
     *
     * @param customerNIF The {@link NIF} identifying the customer.
     * @return An {@link Optional} containing the list of shows, or empty if the customer has none.
     * @throws RuntimeException if an I/O error occurs or the customer was not found.
     */
    public Optional<List<ShowDTO>> getShows4Customer(NIF customerNIF) {
        try {
            Gson gson = new Gson();

            // Step 1: Send operation command
            authController.sendMessage("FindShow4Customer");

            // Step 2: Send customer NIF wrapped as DTO in JSON
            ObjectDTO<NIF> objectDTO = ObjectDTO.of(customerNIF);
            String objectDTOJson = gson.toJson(objectDTO);
            authController.sendMessage(objectDTOJson);

            // Step 3: Receive number of shows
            String response = authController.receiveMessage();
            Type objectDTOType = new TypeToken<ObjectDTO<Integer>>() {}.getType();
            ObjectDTO<Integer> objectDTOReponse = gson.fromJson(response, objectDTOType);
            int numberOfShows = objectDTOReponse.getObject();

            if (numberOfShows == -1) {
                throw new RuntimeException("✖ The system couldn't find the related Customer!");
            } else if (numberOfShows == 0) {
                return Optional.empty();
            }

            // Step 4: Receive each ShowDTO
            List<ShowDTO> listShows = new LinkedList<>();
            for (int i = 0; i < numberOfShows; i++) {
                response = authController.receiveMessage();
                objectDTOType = new TypeToken<ShowDTO>() {}.getType();
                ShowDTO objectDTOReponseShow = gson.fromJson(response, objectDTOType);

                listShows.add(objectDTOReponseShow);
            }

            return Optional.of(listShows);

        } catch (IOException e) {
            throw new RuntimeException("✖ Error trying to communicate with the Server Host!", e);
        }
    }
}
