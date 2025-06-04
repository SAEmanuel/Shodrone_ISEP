package ui.show;

import controller.show.CheckShowDatesController;
import domain.entity.Show;
import utils.AuthUtils;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class CheckShowDatesUI implements Runnable {
    private final CheckShowDatesController controller;

    public CheckShowDatesUI() {
        this.controller = new CheckShowDatesController();
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("CHECK SHOW DATES");

        try {
            Optional<List<Show>>  listOfShows= controller.getShowsForCustomer(AuthUtils.getCurrentUserEmail());

            if(listOfShows.isEmpty()){
                Utils.printFailMessage("\n✖️ Check Show Dates for Customer Can't be Loaded!");
            }else{
                Utils.printSuccessMessage("\n✔️ Check Show Dates for Customer Successfully Loaded!");
                for(Show show : listOfShows.get()){
                    System.out.println(show.toString());
                }
            }

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }
    }
}
