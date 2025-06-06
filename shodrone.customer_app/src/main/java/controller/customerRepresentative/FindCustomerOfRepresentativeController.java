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

public class FindCustomerOfRepresentativeController {

    private final AuthenticationController authController;

    public FindCustomerOfRepresentativeController(AuthenticationController authController){
        this.authController = authController;
    }

    public Optional<NIF> getCustomerIDbyHisEmail(String email) {
        try {
            Gson gson = new Gson();

            authController.sendMessage("FindCustomerOfRepresentative");

            ObjectDTO<String> objectDTO = ObjectDTO.of(email);
            String objectDTOJson = gson.toJson(objectDTO);
            authController.sendMessage(objectDTOJson);

            String response = authController.receiveMessage();
            Type objectDTOType = new TypeToken<ObjectDTO<NIF>>() {}.getType();
            ObjectDTO<NIF> objectDTOReponse = gson.fromJson(response, objectDTOType);
            NIF costumerNIF = objectDTOReponse.getObject();


            if(costumerNIF == null){
                return Optional.empty();
            }

            return Optional.of(costumerNIF);

        } catch (IOException e) {
            System.out.println(ANSI_LIGHT_RED + "Communication with server failed..." + ANSI_RESET);
            return Optional.empty();
        }
    }

}
