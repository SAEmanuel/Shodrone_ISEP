package ui.show;

import controller.network.AuthenticationController;
import controller.show.CheckShowDatesController;
import domain.valueObjects.NIF;
import network.ShowDTO;
import utils.Utils;

import java.util.List;
import java.util.Optional;

import static more.ColorfulOutput.*;
import static more.TextEffects.*;

public class CheckShowDatesUI implements Runnable {
    private final CheckShowDatesController controller;

    public CheckShowDatesUI(AuthenticationController authController) {
        this.controller = new CheckShowDatesController(authController);
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("CHECK SHOW DATES");

        try {
            String email = AuthenticationController.getEmailLogin();
            System.out.printf("%s%s• Email:%s      %s\n",BOLD,ANSI_BRIGHT_BLUE,ANSI_RESET,email);

            NIF customerFound = controller.getCustomerNIFOfTheRepresentativeAssociated(email);
            System.out.printf("%s%s• Customer:%s   %s\n",BOLD,ANSI_BRIGHT_BLUE,ANSI_RESET,customerFound);
            Optional<List<ShowDTO>>  listOfShows = controller.getShowsForCustomer(customerFound);

            if(listOfShows.isEmpty()){
                Utils.printFailMessage("\n✖ No shows found for the associated customer to representative with Email [" + email + "].");
            }else{
                int size = listOfShows.get().size();
                System.out.printf("%s%s• Nº of Show:%s %s\n",BOLD,ANSI_BRIGHT_BLUE,ANSI_RESET,size);

                if(size > 0){
                    System.out.printf("\n\n%s         🧾 SHOW'S INFORMATION SUMMARY                   %s",ANSI_MEDIUM_SPRING_GREEN,ANSI_RESET);
                    System.out.printf("\n%s──────────────────────────────────────────────────%s\n\n",ANSI_BRIGHT_BLACK,ANSI_RESET);

                    int i = 1;
                    for(ShowDTO show : listOfShows.get()){
                        if(show != null){
                            System.out.printf("Show Nº%s%-3s %s%n",i++,":",show.toString());
                        }
                    }
                }

                Utils.printSuccessMessage("\n✔ Check Show Dates for Customer Successfully Loaded!");
            }

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }
    }
}
