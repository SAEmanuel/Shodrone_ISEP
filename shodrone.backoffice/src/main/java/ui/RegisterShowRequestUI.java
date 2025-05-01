package ui;

import controller.RegisterShowRequestController;
import utils.Utils;


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

            registerShowcontroller.registerShowRequest();
        }catch(Exception e){}
    }


}
