package ui;

import controller.ListCostumersController;
import controller.RegisterShowRequestController;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class RegisterShowRequestUI implements Runnable{

    private RegisterShowRequestController registerShowcontroller;
    private ListCostumersController listcustomerscontroller;

    public RegisterShowRequestUI() {
        registerShowcontroller = new RegisterShowRequestController();
        listcustomerscontroller = new ListCostumersController();
    }

    private RegisterShowRequestController getRegisterShowcontroller() {
        return registerShowcontroller;
    }
    private ListCostumersController getListcustomerscontroller() {
        return listcustomerscontroller;
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("REGISTER SHOW REQUEST");
        try{
            foundCustomersUI();
            registerShowcontroller.registerShowRequest();
        }catch(Exception e){}
    }

    private void foundCustomersUI() {
        List<String> options = new ArrayList<>();
        options.add("Search Customer by ID");
        options.add("Search Customer by Name");
        options.add("Search Customer by NIF");
        options.add("Show All Customers");
        int option = Utils.showAndSelectIndexCustomOptions(options, "Search Customer");

        switch (option) {
            case 1:
                getListcustomerscontroller().foundCustomerByID();
                break;
            case 2:
                getListcustomerscontroller().foundCustomerByName();
                break;
            case 3:
                getListcustomerscontroller().foundCustomerByNIF();
                break;
            case 4:
                getListcustomerscontroller().getAllCustomer();
                break;
            default:
                Utils.exitImediately("Cannot register a show request without a customer.");
        }
    }

}
