package controller.proposals;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.network.AuthenticationController;
import network.ObjectDTO;
import network.ResponseDTO;
import network.ShowProposalDTO;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;

import static more.ColorfulOutput.ANSI_LIGHT_RED;
import static more.ColorfulOutput.ANSI_RESET;

public class ProposalResponseController {

    private final AuthenticationController authController;

    public ProposalResponseController(AuthenticationController authenticationController) {
        this.authController = authenticationController;
    }

    public Optional<ResponseDTO> sendResponse(ShowProposalDTO showProposalDTO) {
        try {
            Gson gson = new Gson();

            authController.sendMessage("AnalyseProposalResponse");

            ObjectDTO<ShowProposalDTO> requestDTO = ObjectDTO.of(showProposalDTO);
            String requestJson = gson.toJson(requestDTO);
            authController.sendMessage(requestJson);

            String responseJson = authController.receiveMessage();

            Type responseType = new TypeToken<ObjectDTO<ResponseDTO>>() {}.getType();
            ObjectDTO<ResponseDTO> responseWrapper = gson.fromJson(responseJson, responseType);

            if (responseWrapper == null || responseWrapper.getObject() == null) {
                System.out.println(ANSI_LIGHT_RED + "Invalid server response" + ANSI_RESET);
                return Optional.empty();
            }

            return Optional.of(responseWrapper.getObject());

        } catch (IOException e) {
            System.out.println(ANSI_LIGHT_RED + "Communication error: " + e.getMessage() + ANSI_RESET);
            return Optional.empty();
        }
    }

}
