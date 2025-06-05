package controller.Representative;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class FindCustomerOfRepresentativeController {

    public static void getCustomerOfRepresentativeAction(BufferedReader in, PrintWriter out,Gson gson) {
        try {

            System.out.printf("%s%sWaiting for client connection...%s%n", BOLD, ANSI_BRIGHT_PURPLE, ANSI_RESET);

            String receivedEmail = in.readLine();
            Type objectDTOType = new TypeToken<ObjectDTO<String>>() {}.getType();
            ObjectDTO<String> objectDTO = gson.fromJson(receivedEmail, objectDTOType);

            String email = objectDTO.getObject();

            Optional<Costumer> optionalCustomer = RepositoryProvider.customerRepresentativeRepository().getAssociatedCustomer(email);

            if(optionalCustomer.isPresent()) {
                NIF nif =  optionalCustomer.get().nif();
                System.out.println(nif);
                ObjectDTO<NIF> objectDTOToSend = ObjectDTO.of(nif);
                String objectDTOJsonToSend = gson.toJson(objectDTOToSend);
                out.println(objectDTOJsonToSend);
            }else{
                ObjectDTO<NIF> objectDTOToSend = ObjectDTO.of(null);
                String objectDTOJsonToSend = gson.toJson(objectDTOToSend);
                out.println(objectDTOJsonToSend);
            }



            System.out.printf("%s%sAction finished - Get Customer of Representative - %s%s\n", BOLD, ANSI_BRIGHT_PURPLE, email, ANSI_RESET);
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

}
