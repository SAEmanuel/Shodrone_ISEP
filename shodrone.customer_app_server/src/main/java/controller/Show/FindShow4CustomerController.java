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

public class FindShow4CustomerController {

    public static void getShow4CustomerAction(BufferedReader in, PrintWriter out, Gson gson) {
        try {

            String receivedEmail = in.readLine();
            Type objectDTOType = new TypeToken<ObjectDTO<NIF>>() {}.getType();
            ObjectDTO<NIF> objectDTO = gson.fromJson(receivedEmail, objectDTOType);

            NIF nifCustomer = objectDTO.getObject();

            Optional<Costumer> customerToAssociatedNIF = RepositoryProvider.costumerRepository().findByNIF(nifCustomer);

            if(customerToAssociatedNIF.isPresent()){
                Optional<List<Show>> listOfShowsOptional = RepositoryProvider.showRepository().findByCostumer(customerToAssociatedNIF.get());


                if(listOfShowsOptional.isPresent()) {
                    List<Show> listOfShows = listOfShowsOptional.get();
                    int numShows = listOfShows.size();

                    ObjectDTO<Integer> objectDTOToSendInformative = ObjectDTO.of(numShows);
                    String objectDTOJsonToSendInformative = gson.toJson(objectDTOToSendInformative);
                    out.println(objectDTOJsonToSendInformative);

                    for(int i = 0; i < numShows; i++) {
                        ShowDTO objectDTOToSend = ShowDTO.fromEntity(listOfShows.get(i));
                        String objectDTOJsonToSend = gson.toJson(objectDTOToSend);
                        out.println(objectDTOJsonToSend);
                    }

                }else{
                    int numShows = 0;

                    ObjectDTO<Integer> objectDTOToSendInformative = ObjectDTO.of(numShows);
                    String objectDTOJsonToSendInformative = gson.toJson(objectDTOToSendInformative);
                    out.println(objectDTOJsonToSendInformative);
                }

            }else{
                int numShows = -1;
                ObjectDTO<Integer> objectDTOToSendInformative = ObjectDTO.of(numShows);
                String objectDTOJsonToSendInformative = gson.toJson(objectDTOToSendInformative);
                out.println(objectDTOJsonToSendInformative);
            }

            System.out.printf("%s%s❕SERVER ACTION FINISHED: Get Show for Customer of Representative -> [%s] %s%n",ANSI_BRIGHT_PURPLE,BOLD,nifCustomer,ANSI_RESET);

        } catch (IOException e) {
            Utils.printFailMessage("Client error: " + e.getMessage());
        }
    }
}
