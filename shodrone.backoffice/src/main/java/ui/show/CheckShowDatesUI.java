package ui.show;

import controller.show.CheckShowDatesController;
import domain.entity.Figure;
import utils.AuthUtils;
import utils.Utils;

public class CheckShowDatesUI implements Runnable{
    private final CheckShowDatesController controller;

    public CheckShowDatesUI(CheckShowDatesController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("CHECK SHOW DATES");

        try{

//            if(optionalResult.isEmpty()){
//                Utils.printFailMessage("\n✖️ Something went grong saving the Show Proposal!");
//            }else{
//                Utils.printSuccessMessage("\n✔️ Figures added successfully to proposal!");
//                for(Figure figure : optionalResult.get().getSequenceFigues()){
//                    System.out.println(figure.toString());
//                }
//            }

        }catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }
    }
}
