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
            Utils.printCenteredSubtitle("Show information");
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

            ShowRequest resultClone = getRegisterShowcontroller().registerShowRequest();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            System.out.printf("\n\n🧾 Show Request with ID [%d] summary: %n", resultClone.identity());
            System.out.println("──────────────────────────────────────────────────");
            System.out.println("⏱️ Submitted at              : " + resultClone.getSubmissionDate().format(formatter));
            System.out.println("👤 Responsible Collaborator  : " + resultClone.getSubmissionAuthor());
            System.out.println("👥 Customer                  : " + resultClone.getCostumer().name());
            System.out.println("📝 Description               : " + resultClone.getDescription());
            System.out.println("📅 Show Date                 : " + resultClone.getShowDate().format(formatter));
            System.out.println("📍 Location                  : " + resultClone.getLocation().toString());
            System.out.println("🚁 Number of Drones          : " + resultClone.getNumberOfDrones());
            System.out.println("⏱️ Show Duration             : " + resultClone.getShowDuration().toMinutes() + " minutes");
            System.out.println("🗿 Selected Figures          :");

            int index = 1;
            for (Figure figure : resultClone.getFigures()) {
                System.out.printf("   %d. %s%n", index++, figure.toString());
            }

            System.out.println("──────────────────────────────────────────────────\n");

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
