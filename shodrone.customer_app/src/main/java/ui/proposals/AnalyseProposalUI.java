package ui.proposals;

import controller.network.AuthenticationController;
import controller.proposals.GetMyProposalsController;
import domain.entity.Show;
import domain.entity.ShowProposal;
import network.ShowProposalDTO;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class AnalyseProposalUI implements Runnable {
    private final GetMyProposalsController myProposalsController;

    public AnalyseProposalUI(AuthenticationController authenticationController) {
        myProposalsController = new GetMyProposalsController(authenticationController);
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("ANALYZE SHOW PROPOSALS");

        try {
            String email = AuthenticationController.getEmailLogin();
            Optional<List<ShowProposalDTO>> correspondingProposals = myProposalsController.getMyProposals(email);
            if (correspondingProposals.isEmpty()) {
                Utils.printFailMessage("\nNo proposals found for the associated customer...");
            } else {
                Utils.printSuccessMessage("\nProposals found successfully!");
                //todo agora poder selecionar, ver o conteudo e aceitar ou rejeitar!
                for (ShowProposalDTO proposal : correspondingProposals.get()) {
                    System.out.printf("Proposal [%d]%n", proposal.getId());
                }
            }

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }

    }
}
