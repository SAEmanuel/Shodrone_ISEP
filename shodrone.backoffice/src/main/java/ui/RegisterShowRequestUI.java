package ui;

import controller.RegisterShowRequestController;
import utils.Utils;

public class RegisterShowRequestUI implements Runnable{

    private final RegisterShowRequestController registerShowcontroller;

    public RegisterShowRequestUI() {
        registerShowcontroller = new RegisterShowRequestController();
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("REGISTER SHOW REQUEST");

        registerShowcontroller.registerShowRequest();
    }

}
