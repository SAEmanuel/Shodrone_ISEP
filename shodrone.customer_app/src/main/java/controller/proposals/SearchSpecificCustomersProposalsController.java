package controller.proposals;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.network.AuthenticationController;
import domain.valueObjects.NIF;
import network.ObjectDTO;
import network.ShowProposalDTO;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static more.ColorfulOutput.ANSI_LIGHT_RED;
import static more.ColorfulOutput.ANSI_RESET;

public class SearchSpecificCustomersProposalsController {
    private final AuthenticationController authController;

    public SearchSpecificCustomersProposalsController(AuthenticationController authenticationController) {
        authController = authenticationController;
    }

    public Optional<List<ShowProposalDTO>> searchProposals(NIF customerNIF) {
        try {
            Gson gson = new Gson();
            authController.sendMessage("FindCustomersProposals");
            ObjectDTO<NIF> objectDTO = ObjectDTO.of(customerNIF);
            String objectDTOJson = gson.toJson(objectDTO);
            authController.sendMessage(objectDTOJson);

            String response = authController.receiveMessage();

            Type objectDTOType = new TypeToken<ObjectDTO<Integer>>() {}.getType();
            ObjectDTO<Integer> objectDTOResponse = gson.fromJson(response, objectDTOType);
            int numberOfProposals = objectDTOResponse.getObject();

            if (numberOfProposals == 0) {
                return Optional.empty();
            }

            List<ShowProposalDTO> proposals = new ArrayList<>();
            for (int i = 0; i < numberOfProposals; i++) {
                response = authController.receiveMessage();

                objectDTOType = new TypeToken<ObjectDTO<ShowProposalDTO>>() {}.getType();
                ObjectDTO<ShowProposalDTO> objectDTOResponseShowProposal =
                        gson.fromJson(response, objectDTOType);

                if (objectDTOResponseShowProposal == null) {
                    continue;
                }

                ShowProposalDTO proposal = objectDTOResponseShowProposal.getObject();
                if (proposal == null) {
                    continue;
                }

                proposals.add(proposal);
            }

            return Optional.of(proposals);

        } catch (IOException e) {
            System.out.println(ANSI_LIGHT_RED + "Error communicating with server..." + ANSI_RESET);
            return Optional.empty();
        }
    }

}
