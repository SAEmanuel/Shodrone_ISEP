package ui;

import controller.RegisterShowRequestController;
import domain.entity.Figure;
import domain.entity.ShowRequest;
import domain.valueObjects.Location;
import utils.Utils;

import java.time.format.DateTimeFormatter;

import static more.ColorfulOutput.ANSI_BRIGHT_BLACK;
import static more.ColorfulOutput.ANSI_RESET;
import static more.TextEffects.ITALIC;


public class RegisterShowRequestUI implements Runnable{
    private final RegisterShowRequestController registerShowcontroller;

    public RegisterShowRequestUI() {
        registerShowcontroller = new RegisterShowRequestController();
    }

    private RegisterShowRequestController getRegisterShowcontroller() {
        return registerShowcontroller;
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("REGISTER SHOW REQUEST");
        try{
            Utils.printCenteredSubtitle("Costumer information");
            getRegisterShowcontroller().foundCostumerForRegistration();
            Utils.printCenteredSubtitle("Figures selection for show");
            getRegisterShowcontroller().foundFiguresForRegistration();
            Utils.printCenteredSubtitle("Show description");
            getRegisterShowcontroller().getDescriptionsForRegistration(Utils.readLineFromConsole(ANSI_BRIGHT_BLACK+ITALIC+"• Enter the description of the show"+ANSI_RESET));
            Utils.printCenteredSubtitle("Show location");
            requestLocationInformation();
            Utils.printCenteredSubtitle("Show date");
            getRegisterShowcontroller().getDateForShow();
            Utils.printCenteredSubtitle("Drone information");
            getRegisterShowcontroller().getNumberOfDrones();
            Utils.printCenteredSubtitle("Show duration");
            getRegisterShowcontroller().getShowDuration();

            ShowRequest registeredShowRequest = getRegisterShowcontroller().registerShowRequest();
            Utils.printShowRequestResume(registeredShowRequest);
            Utils.printSuccessMessage("\n✅ Show request successfully registered!");

        }catch(Exception e){
            Utils.printAlterMessage(e.getMessage());
        }
    }

    private void requestLocationInformation() {
        Utils.printSubTitle("Location information");
        getRegisterShowcontroller().getLocationOfShow();
    }


}
