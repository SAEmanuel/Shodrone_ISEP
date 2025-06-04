package controller.customerRepresentative;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.network.AuthenticationController;
import domain.entity.Costumer;
import network.ObjectDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Optional;

import static more.ColorfulOutput.ANSI_LIGHT_RED;
import static more.ColorfulOutput.ANSI_RESET;

public class FindCustomerOfRepresentativeController {

    public FindCustomerOfRepresentativeController(){}

    public Optional<Costumer> getCustomerIDbyHisEmail(String email) {
        try (Socket socket = new Socket(AuthenticationController.getServerHost(), AuthenticationController.getServerPort());
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("FindCustomerOfRepresentative");

            ObjectDTO<String> objectDTO = ObjectDTO.of(email);
            Gson gson = new Gson();
            String objectDTOJson = gson.toJson(objectDTO);
            out.println(objectDTOJson);

            String response = in.readLine();

            Type type = new TypeToken<ObjectDTO<Optional<Costumer>>>() {}.getType();
            ObjectDTO<Optional<Costumer>> objectDTOOptional = gson.fromJson(response, type);

            return objectDTOOptional.getObject();

        } catch (IOException e) {
            System.out.println(ANSI_LIGHT_RED + "Servers are down! Login unavailable..." + ANSI_RESET);
            System.exit(1);
            return Optional.empty();
        }
    }




}
