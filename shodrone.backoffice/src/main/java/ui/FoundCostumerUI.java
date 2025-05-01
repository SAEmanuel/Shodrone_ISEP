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
            foundCustomersUI();
    }

    public Optional<?> foundCustomersUI() {
        List<String> options = new ArrayList<>();
        options.add("Search Customer by ID");
        options.add("Search Customer by NIF");
        options.add("Show All Customers");
        int option = Utils.showAndSelectIndexCustomOptions(options, "Search Customer");
        Optional<?> optionalResult = Optional.empty();

        switch (option) {
            case 0:
                optionalResult = foundUniqueCostumerIDUI(option);
                break;
            case 1:
                optionalResult = foundUniqueCostumerNIFUI(option);
                break;
            case 2:
                optionalResult = foundListCostumerUI(option);
                break;
            default:
            Utils.exitImediately("Cannot register a show request without a customer.");
        }
        didMethodFoundCostumer(optionalResult);
        return optionalResult;
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
            Utils.printOptionalValidMessage("Customers found:",result);
        }else{
            Utils.exitImediately("No customer/s found in system. Please register a new customer in 'Register Customer' menu option.");
        }
    }
}