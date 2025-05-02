package ui;

import controller.ListShowRequestByCostumerController;
import domain.entity.ShowRequest;
import utils.Utils;

import java.util.List;

public class ListShowRequestByCostumerUI implements Runnable {
    private final ListShowRequestByCostumerController controller;

    public ListShowRequestByCostumerUI() {
        controller = new ListShowRequestByCostumerController();
    }
    private ListShowRequestByCostumerController getRegisterShowcontroller() {
        return controller;
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("LIST SHOW REQUEST OF COSTUMER");
        try{
            Utils.printCenteredSubtitle("Costumer information");
            List<ShowRequest> showRequestListByCostumer = getRegisterShowcontroller().listShowRequestByCostumer();

            Utils.printCenteredSubtitle("Show's information");
            for (ShowRequest showRequest : showRequestListByCostumer) {
                Utils.printShowRequestResume(showRequest);
                Utils.dropLines(2);
            }

        }catch(Exception e){
            Utils.printAlterMessage(e.getMessage());
        }
    }
}
