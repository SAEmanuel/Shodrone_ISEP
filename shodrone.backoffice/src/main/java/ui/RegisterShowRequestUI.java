package ui;

import controller.ListCostumersController;
import controller.RegisterShowRequestController;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class RegisterShowRequestUI implements Runnable{

    private final RegisterShowRequestController registerShowcontroller;
    private final FoundCostumerUI foundCostumerUI;

    public RegisterShowRequestUI() {
        registerShowcontroller = new RegisterShowRequestController();
        foundCostumerUI = new FoundCostumerUI();
    }
    private RegisterShowRequestController getRegisterShowcontroller() {
        return registerShowcontroller;
    }
    private FoundCostumerUI getFoundCostumerUI() {
        return foundCostumerUI;
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("REGISTER SHOW REQUEST");
        try{
            getFoundCostumerUI().foundCustomersUI();
            registerShowcontroller.registerShowRequest();
        }catch(Exception e){}
    }


}
