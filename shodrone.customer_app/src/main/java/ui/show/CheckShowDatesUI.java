package ui.show;

import controller.network.AuthenticationController;
import controller.show.CheckShowDatesController;
import domain.entity.Show;
import network.ShowDTO;
import utils.Utils;

import java.util.List;
import java.util.Optional;

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
            System.out.println("Email: " + email);
            Optional<List<ShowDTO>>  listOfShows = controller.getShowsForCustomer(email);

            if(listOfShows.isEmpty()){
                Utils.printFailMessage("\n✖ No shows found for the associated customer to representative with Email [" + email + "].");
            }else{
                System.out.println(listOfShows.get().size());
                for(ShowDTO show : listOfShows.get()){
                    if(show != null){
                        System.out.println(show);
                    }
                }
                Utils.printSuccessMessage("\n✔ Check Show Dates for Customer Successfully Loaded!");
            }

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }
    }
}
