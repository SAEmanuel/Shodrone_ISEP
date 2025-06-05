package controller.show;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.network.AuthenticationController;
import domain.entity.Show;
import domain.valueObjects.NIF;
import network.ObjectDTO;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static more.ColorfulOutput.ANSI_LIGHT_RED;
import static more.ColorfulOutput.ANSI_RESET;

public class FindShows4CustomerController {

    private final AuthenticationController authController;

    public FindShows4CustomerController(AuthenticationController authController){
        this.authController = authController;
    }

    public Optional<List<Show>> getShows4Customer(NIF customerNIF) {
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

            System.out.println(numberOfShows);

            if(numberOfShows == -1){
                System.out.println("Algum problema com encontrar o customer");
                return Optional.empty();
            }else if(numberOfShows == 0){
                return Optional.empty();
            }

            List<Show> listShows = new LinkedList<>();

            for(int i = 0 ; i < numberOfShows; i++){
                response = authController.receiveMessage();
                objectDTOType = new TypeToken<ObjectDTO<Show>>() {}.getType();
                ObjectDTO<Show> objectDTOReponseShow = gson.fromJson(response, objectDTOType);
                Show show = objectDTOReponseShow.getObject();

                listShows.add(show);
            }


            return Optional.of(listShows);

        } catch (IOException e) {
            System.out.println(ANSI_LIGHT_RED + "Erro na comunicação com o servidor..." + ANSI_RESET);
            return Optional.empty();
        }
    }

}
