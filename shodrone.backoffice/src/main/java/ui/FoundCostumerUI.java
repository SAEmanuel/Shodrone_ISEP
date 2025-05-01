package ui;

import controller.ListCostumersController;
import domain.entity.Costumer;
import utils.Utils;
import utils.Validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FoundCostumerUI implements Runnable {
    private final ListCostumersController listcustomerscontroller;

    public FoundCostumerUI() {
        listcustomerscontroller = new ListCostumersController();
    }
    private ListCostumersController getListcustomerscontroller() {
        return listcustomerscontroller;
    }


    @Override
    public void run() {
        try {
            foundCustomersUI();
        }catch(Exception e){}
    }

    public Optional<Costumer> foundCustomersUI() {
        List<String> options = new ArrayList<>();
        options.add("Search Customer by ID");
        options.add("Search Customer by NIF");
        options.add("Show All Customers");
        int option = Utils.showAndSelectIndexCustomOptions(options, "Search Customer");
        Optional<?> optionalResult = Optional.empty();

        switch (option) {
            case 0:
                optionalResult = foundUniqueCostumerIDUI(option);
                didMethodFoundCostumer(optionalResult);
                break;
            case 1:
                optionalResult = foundUniqueCostumerNIFUI(option);
                didMethodFoundCostumer(optionalResult);
                break;
            case 2:
                optionalResult = foundListCostumerUI(option);
                optionalResult = Utils.showAndSelectObjectFromList((Optional<List<?>>) optionalResult);
                break;
            default:
                Utils.exitImmediately("Cannot register a show request without a customer.");
        }
        return (Optional<Costumer>) optionalResult;
    }

    private Optional<Costumer> foundUniqueCostumerIDUI(int option) {
        return getListcustomerscontroller().foundCustomerByID(Utils.readLongFromConsole("Insert the Customer ID:"));
    }
    private Optional<Costumer> foundUniqueCostumerNIFUI(int option) {
        return getListcustomerscontroller().foundCustomerByNIF(Utils.readNIFFromConsole("Insert the Customer NIF:"));
    }
    private Optional<List<Costumer>> foundListCostumerUI(int option){
        return getListcustomerscontroller().getAllCustomer();
    }

    private void didMethodFoundCostumer(Optional<?> result){
        if(Validations.isNotEmptyOptional(result)){
            Utils.printOptionalValidMessage("Customers found",result);
        }else{
            Utils.exitImmediately("No customer/s found in system. Please register a new customer in 'Register Customer' menu option.");
        }
    }
}