package controller.customerRepresentative;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.network.AuthenticationController;
import domain.entity.Costumer;
import domain.valueObjects.NIF;
import network.ObjectDTO;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;

import static more.ColorfulOutput.ANSI_LIGHT_RED;
import static more.ColorfulOutput.ANSI_RESET;

/**
 * Controller responsible for retrieving the NIF of the customer associated
 * with a specific representative's email address.
 * <p>
 * This controller handles the request/response cycle with the server using
 * the {@link AuthenticationController} and Gson for JSON serialization.
 */
public class FindCustomerOfRepresentativeController {

    /** Authenticated session to communicate with the server. */
    private final AuthenticationController authController;

    /**
     * Constructs a new controller with a given authentication session.
     *
     * @param authController the authentication controller managing the session
     */
    public FindCustomerOfRepresentativeController(AuthenticationController authController){
        this.authController = authController;
    }

    /**
     * Retrieves the NIF of the customer associated with the given representative's email.
     * <p>
     * Sends a request to the server and deserializes the response into a {@link NIF}.
     *
     * @param email The email address of the representative.
     * @return An {@link Optional} containing the customer's NIF, or {@code Optional.empty()} if not found.
     */
    public Optional<NIF> getCustomerIDbyHisEmail(String email) {
        try {
            Gson gson = new Gson();

            // Send request header to indicate the desired operation
            authController.sendMessage("FindCustomerOfRepresentative");

            // Wrap and send the email as an ObjectDTO
            ObjectDTO<String> objectDTO = ObjectDTO.of(email);
            String objectDTOJson = gson.toJson(objectDTO);
            authController.sendMessage(objectDTOJson);

            // Receive the server's response
            String response = authController.receiveMessage();
            Type objectDTOType = new TypeToken<ObjectDTO<NIF>>() {}.getType();
            ObjectDTO<NIF> objectDTOReponse = gson.fromJson(response, objectDTOType);
            NIF costumerNIF = objectDTOReponse.getObject();

            // Return the result wrapped in Optional
            if (costumerNIF == null) {
                return Optional.empty();
            }

            return Optional.of(costumerNIF);

        } catch (IOException e) {
            System.out.println(ANSI_LIGHT_RED + "Communication with server failed..." + ANSI_RESET);
            return Optional.empty();
        }
    }

}
