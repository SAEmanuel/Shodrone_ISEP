package controller.proposals;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.entity.ShowProposal;
import network.ObjectDTO;
import network.ResponseDTO;
import network.ShowProposalDTO;
import persistence.RepositoryProvider;
import utils.Utils;

import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Optional;

import static more.ColorfulOutput.ANSI_BRIGHT_PURPLE;
import static more.ColorfulOutput.ANSI_RESET;
import static more.TextEffects.BOLD;

public class AnalyseProposalResponse {
    public static void analyseProposalResponseAction(String proposalJson, PrintWriter out, Gson gson) {
        try {

            Type objectDTOType = new TypeToken<ObjectDTO<ShowProposalDTO>>() {}.getType();
            ObjectDTO<ShowProposalDTO> objectDTO = gson.fromJson(proposalJson, objectDTOType);

            if (objectDTO == null || objectDTO.getObject() == null) {
                sendResponse(out, gson, "Invalid request: No data received");
                return;
            }

            ShowProposalDTO proposalDTO = objectDTO.getObject();
            System.out.printf("%s🛜 [SERVER] : Received update request for proposal ID: %s%s%n",BOLD, proposalDTO.getId(),ANSI_RESET);

            Optional<ShowProposal> optionalShowProposal =
                    RepositoryProvider.showProposalRepository().findByID(proposalDTO.getId());

            if (optionalShowProposal.isEmpty()) {
                sendResponse(out, gson, "Proposal not found: " + proposalDTO.getId());
                Utils.printFailMessage("Unable to find proposal...");
                return;
            }

            ShowProposal proposal = optionalShowProposal.get();
            proposal.setStatus(proposalDTO.getStatus());

            Optional<ShowProposal> updatedProposal =
                    RepositoryProvider.showProposalRepository().saveInStore(proposal);

            if (updatedProposal.isPresent()) {
                System.out.printf("%s🛜 [SERVER] : Status updated to: %s%s%n",BOLD, proposal.getStatus(),ANSI_RESET);
                sendResponse(out, gson, "success");
            } else {
                sendResponse(out, gson, "fail");
            }

            System.out.printf("%s%s❕SERVER ACTION FINISHED: Analyse Proposal Response -> [%s] %s%n",ANSI_BRIGHT_PURPLE,BOLD,proposal.getNameProposal(),ANSI_RESET);

        } catch (Exception e) {
            System.out.println("Error processing proposal: " + e.getMessage());
            sendResponse(out, gson, "Internal server error");
        }
    }

    private static void sendResponse(PrintWriter out, Gson gson, String message) {
        ResponseDTO responseDTO = new ResponseDTO(message);
        ObjectDTO<ResponseDTO> responseObject = ObjectDTO.of(responseDTO);
        out.println(gson.toJson(responseObject));
    }
}

