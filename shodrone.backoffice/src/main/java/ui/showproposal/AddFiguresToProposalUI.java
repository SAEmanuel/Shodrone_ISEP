package ui.showproposal;

import controller.service.ServiceForValidSequenceFiguresForShow;
import controller.showproposal.AddFiguresToProposalController;
import domain.entity.Figure;
import domain.entity.ShowProposal;
import utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static more.ColorfulOutput.*;

public class AddFiguresToProposalUI implements Runnable {
    private final AddFiguresToProposalController controller;


    public AddFiguresToProposalUI() {
        controller = new AddFiguresToProposalController();
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("ADD FIGURE TO SHOW PROPOSAL");

        try {
            Utils.printCenteredSubtitle("Show Proposal Information");
            List<ShowProposal> listOfProposals = controller.getAllProposals();
            int selectedProposalIndex = Utils.showAndSelectIndexCustomOptions(listOfProposals, "Select one Show Proposal");
            ShowProposal selectedProposal = listOfProposals.get(selectedProposalIndex);
            Utils.printSuccessMessage("\nSelected Proposal with ID : " + selectedProposal.identity());

            Utils.printCenteredSubtitle("Figures of Selected Proposal");
            Queue<Figure> listFigures = selectedProposal.getSequenceFigues();

            if(listFigures.isEmpty()){
                Utils.printAlterMessage("No Figures was found in the selected proposal, do you wish to add [y/n]: ");
            }else{
                Utils.printAlterMessage("A total of "+listFigures.size()+" Figures were found in the selected proposal: ");
                int count = 0;
                for(Figure figure: listFigures){
                    System.out.printf("    %s%s %s %s",ANSI_BRIGHT_BLACK,"•",ANSI_RESET,figure);
                }
                Utils.dropLines(1);
            }

            Utils.printCenteredSubtitle("Selection of New Figures");
            Queue<Figure> sequenceOfFigures = ServiceForValidSequenceFiguresForShow.getListFiguresUIWithRepetitions(selectedProposal.getShowRequest().getCostumer(), listFigures);
            Optional<ShowProposal> optionalResult = controller.saveNewImagesInProposal(selectedProposal,sequenceOfFigures);

            if(optionalResult.isEmpty()){
                Utils.printFailMessage("\n✖️ Something went grong saving the Show Proposal!");
            }else{
                Utils.printSuccessMessage("\n✔️ Figures added successfully to proposal!");
                for(Figure figure : optionalResult.get().getSequenceFigues()){
                    System.out.println(figure.toString());
                }
            }
            Utils.waitForUser();
        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }

    }
}
