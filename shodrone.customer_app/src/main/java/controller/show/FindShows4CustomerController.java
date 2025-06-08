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


public class FindShows4CustomerController {

    private final AuthenticationController authController;

    public FindShows4CustomerController(AuthenticationController authController){
        this.authController = authController;
    }

    public Optional<List<ShowDTO>> getShows4Customer(NIF customerNIF) {
        try {
            Gson gson = new Gson();

            authController.sendMessage("FindShow4Customer");

            ObjectDTO<NIF> objectDTO = ObjectDTO.of(customerNIF);
            String objectDTOJson = gson.toJson(objectDTO);
            authController.sendMessage(objectDTOJson);

            String response = authController.receiveMessage();
            Type objectDTOType = new TypeToken<ObjectDTO<Integer>>() {}.getType();
            ObjectDTO<Integer> objectDTOReponse = gson.fromJson(response, objectDTOType);
            int numberOfShows = objectDTOReponse.getObject();

            if(numberOfShows == -1){
                throw new RuntimeException("✖ The system couldn't found the related Customer!");
            }else if(numberOfShows == 0){
                return Optional.empty();
            }

            List<ShowDTO> listShows = new LinkedList<>();

            for(int i = 0 ; i < numberOfShows; i++){
                response = authController.receiveMessage();
                objectDTOType = new TypeToken<ShowDTO>() {}.getType();
                ShowDTO objectDTOReponseShow = gson.fromJson(response, objectDTOType);

                listShows.add(objectDTOReponseShow);
            }


            return Optional.of(listShows);

        } catch (IOException e) {
            throw new RuntimeException("✖ Error trying to comunicated with the Server Host!");
        }
    }

}
