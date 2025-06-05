package ui.showproposal;

import controller.showproposal.AddVideoOfSimulationToTheProposalController;
import domain.entity.ShowProposal;
import domain.valueObjects.*;
import javafx.application.Application;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public class AddVideoOfSimulationToTheProposalUI implements Runnable {

    private final AddVideoOfSimulationToTheProposalController controller;

    public AddVideoOfSimulationToTheProposalUI() {
        controller = new AddVideoOfSimulationToTheProposalController();
    }

    @Override
    public void run() {
        try {
            Utils.printCenteredTitle("ADD VIDEO TO SHOW PROPOSAL");
            Utils.dropLines(3);

            String videoFile = Utils.rePromptWhileInvalid("Enter the video file name: ", String::new);

            Drone3DSimulation.loadDroneDataFromCustomFormat(videoFile);
            Application.launch(Drone3DSimulation.class);

            File file = new File("drone_" + videoFile + ".mov");
            byte[] content = Files.readAllBytes(file.toPath());
            Optional<Video> video = Optional.of(new Video(content));

            List<ShowProposal> listOfProposals = controller.getAllProposals();

            Optional<ShowProposal> result = Optional.empty();

            if(!listOfProposals.isEmpty() || listOfProposals == null){
                int selectedProposalIndex = Utils.showAndSelectIndexCustomOptions(listOfProposals, "Select one Show Proposal");
                ShowProposal selectedProposal = listOfProposals.get(selectedProposalIndex);
                Utils.printSuccessMessage("\nSelected Proposal with ID : " + selectedProposal.identity());

                result = controller.addVideoToShowProposal(selectedProposal, video.get());
            }
            else{
                Utils.printFailMessage("\nNo Show Proposal");
                return;
            }

            if(result.isEmpty()){
                Utils.printFailMessage("\n✖️ Something went wrong saving the Show Proposal!");
            }else{
                Utils.printSuccessMessage("\n✔️ Video added successfully to proposal!");
            }
        } catch (IOException e) {
            System.err.println("Error reading the video file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
        Utils.waitForUser();
    }
}