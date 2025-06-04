package controller.Representative;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.entity.Costumer;
import network.ObjectDTO;
import persistence.RepositoryProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Optional;

import static more.ColorfulOutput.ANSI_BRIGHT_PURPLE;
import static more.ColorfulOutput.ANSI_RESET;
import static more.TextEffects.BOLD;

public class FindCustomerOfRepresentativeController {

    public static void getCustomerOfRepresentativeAction(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            System.out.printf("%s%sWaiting for client connection...%s%n",BOLD,ANSI_BRIGHT_PURPLE, ANSI_RESET);

            String received = in.readLine();
            Gson gson = new Gson();
            Type objectDTOType = new TypeToken<ObjectDTO<String>>() {}.getType();
            ObjectDTO<String> objectDTO = gson.fromJson(received, objectDTOType);

            String email = objectDTO.getObject();

            Optional<Costumer> optionalCustomer = RepositoryProvider.customerRepresentativeRepository().getAssociatedCustomer(email);

            ObjectDTO<Optional<Costumer>> objectDTOToSend = ObjectDTO.of(optionalCustomer);
            String objectDTOJsonToSend = gson.toJson(objectDTOToSend);
            out.println(objectDTOJsonToSend);

            System.out.printf("%s%sAction finished - Get Customer of Representative - %s%s\n",BOLD,ANSI_BRIGHT_PURPLE,email,ANSI_RESET);
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

}
