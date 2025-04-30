package ui;

import controller.ListCostumersController;
import domain.entity.Customer;
import utils.Utils;
import utils.Validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FoundCostumerUI implements Runnable {
    private ListCostumersController listcustomerscontroller;

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

    public void foundCustomersUI() {
        List<String> options = new ArrayList<>();
        options.add("Search Customer by ID");
        options.add("Search Customer by NIF");
        options.add("Show All Customers");
        int option = Utils.showAndSelectIndexCustomOptions(options, "Search Customer");

        if(option == 0 || option == 1){
            foundUniqueCostumerUI(option);
        }else if(option == 2){
            foundListCostumerUI(option);
        }else{
            Utils.exitImediately("Cannot register a show request without a customer.");
        }

    }

    private void foundListCostumerUI(int option) {
        Optional<Customer> customer = Optional.empty();
        switch (option){
            case 1:
                customer = getListcustomerscontroller().foundCustomerByID();
                break;
            default:
                customer = getListcustomerscontroller().foundCustomerByNIF();
        }
        if(Validations.isNotEmptyOptional(customer)){
            Utils.printOptionalValidMessage("Customer found:",customer);
        }else{
            Utils.exitImediately("No customer's found in system. Please register a new customer in 'Register Customer' menu option.");
        }
    }


    public void foundUniqueCostumerUI(int option){
        Optional<List<Customer>> customerList = Optional.empty();
        switch (option){
            case 2:
                customerList =  getListcustomerscontroller().foundCustomerByName();
                break;
            default:
                customerList = getListcustomerscontroller().getAllCustomer();
        }
        if(Validations.isNotEmptyOptional(customerList)){
            Utils.printOptionalValidMessage("Customers found:",customerList);
        }else{
            Utils.exitImediately("No customer found. Please register a new customer in 'Register Customer' menu option.");
        }
    }
}