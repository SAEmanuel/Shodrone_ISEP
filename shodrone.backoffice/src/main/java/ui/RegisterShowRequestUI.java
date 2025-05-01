package ui;

import controller.RegisterShowRequestController;
import domain.valueObjects.Location;
import utils.Utils;

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
            getRegisterShowcontroller().foundCostumerForRegistration();
            getRegisterShowcontroller().foundFiguresForRegistration();

            getRegisterShowcontroller().getDescriptionsForRegistration(Utils.readLineFromConsole(ANSI_BRIGHT_BLACK+ITALIC+"\n• Enter the description of the show"+ANSI_RESET));
            requestLocationInformation();

        }catch(Exception e){}
    }

    private void requestLocationInformation() {
        Utils.printSubTitle("Location information");
        getRegisterShowcontroller().getLocationOfShow();
    }


}
