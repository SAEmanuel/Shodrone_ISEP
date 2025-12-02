package ui.proposals;

import controller.network.AuthenticationController;
import controller.proposals.GetMyProposalsController;
import controller.proposals.ProposalResponseController;
import domain.valueObjects.ShowProposalStatus;
import network.ResponseDTO;
import network.ShowProposalDTO;
import utils.ProposalAnalysisDialog;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class AnalyseProposalUI implements Runnable {
    private final GetMyProposalsController myProposalsController;
    private final ProposalResponseController responseController;

    public AnalyseProposalUI(AuthenticationController authenticationController) {
        myProposalsController = new GetMyProposalsController(authenticationController);
        responseController = new ProposalResponseController(authenticationController);
    }


    @Override
    public void run() {
        Utils.printCenteredTitle("ANALYZE SHOW PROPOSALS");

        try {
            String email = AuthenticationController.getEmailLogin();
            Optional<List<ShowProposalDTO>> correspondingProposals = myProposalsController.getMyProposals(email);
            if (correspondingProposals.isEmpty() || correspondingProposals.get().isEmpty()) {
                Utils.printFailMessage("\nNo proposals found for the associated customer...");
            } else {
                Utils.printSuccessMessage("\nProposals found successfully!");
                int index = Utils.showAndSelectIndexPartially(correspondingProposals.get(), "Select a proposal to analyse");

                if (index == - 1) {
                    Utils.printFailMessage("Operation canceled....");
                    return;
                }

                ProposalAnalysisDialog dialog = new ProposalAnalysisDialog(null, correspondingProposals.get().get(index));
                String result = dialog.showDialog();


                String response = "No feedback provided!";
                boolean feedback;
                Optional<ResponseDTO> responseDTO;

                switch (result) {
                    case "accept":
                        correspondingProposals.get().get(index).setStatus(ShowProposalStatus.CUSTOMER_APPROVED);
                        feedback = Utils.confirm("Do you wish to provide any feedback? (y/n): ");
                        if (feedback) {
                            response = Utils.readLineFromConsole("Feedback");
                        }
                        correspondingProposals.get().get(index).setFeedback(response);

                        responseDTO = responseController.sendResponse(correspondingProposals.get().get(index));

                        if (responseDTO.isPresent()) {
                            if (responseDTO.get().getMessage().equalsIgnoreCase("success")) {
                                Utils.printSuccessMessage("Proposal accepted successfully!");
                            } else {
                                Utils.printFailMessage("Server response: " + responseDTO.get().getMessage());
                            }
                        } else {
                            Utils.printFailMessage("No response from server. Proposal may not have been accepted.");
                        }
                        break;

                    case "reject":
                        correspondingProposals.get().get(index).setStatus(ShowProposalStatus.REJECTED);
                        feedback = Utils.confirm("Do you wish to provide any feedback? (y/n): ");
                        if (feedback) {
                            response = Utils.readLineFromConsole("Feedback");
                        }
                        correspondingProposals.get().get(index).setFeedback(response);

                        responseDTO = responseController.sendResponse(correspondingProposals.get().get(index));

                        if (responseDTO.isPresent()) {
                            if (responseDTO.get().getMessage().equalsIgnoreCase("success")) {
                                Utils.printSuccessMessage("Proposal rejected successfully!");
                            } else {
                                Utils.printFailMessage("Server response: " + responseDTO.get().getMessage());
                            }
                        } else {
                            Utils.printFailMessage("No response from server. Proposal may not have been rejected.");
                        }
                        break;

                    case "download", "exit":
                        break;
                }

            }

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }

    }
}
