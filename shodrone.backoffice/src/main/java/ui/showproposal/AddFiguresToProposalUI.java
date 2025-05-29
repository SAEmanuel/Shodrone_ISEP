package ui.showproposal;

import controller.showproposal.AddFiguresToProposalController;
import domain.entity.ShowProposal;
import utils.Utils;

import java.util.List;

public class AddFiguresToProposalUI implements Runnable{
    private final AddFiguresToProposalController controller;


    public AddFiguresToProposalUI(){
        controller = new AddFiguresToProposalController();
    }

    @Override
    public void run(){
        Utils.printCenteredTitle("ADD FIGURE TO SHOW PROPOSAL");

        try{
            Utils.printCenteredSubtitle("Show Proposal Information");
            List<ShowProposal> listOfProposals =  controller.getAllProposals();
            Utils.showAndSelectIndexCustomOptions(listOfProposals,"Select one Show Proposal");

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }

    }
}
