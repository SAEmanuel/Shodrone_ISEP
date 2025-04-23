package authz;

import authz.controller.AuthenticationController;
import authz.repositories.*;

public class Bootstrap implements Runnable {

    private static final String PROD_MANAGER_EMAIL = "prodm@this.app";
    private static final String PROD_MANAGER_PASSWORD = "manager";


    //Add some task categories to the repository as bootstrap
    public void run() {
        addUsers();
//        addOperations();
//        addIDs();
//        addMachines();
//        addItems();
    }



    //add users to the repository
    private void addUsers() {
        //TODO: add Authentication users here: should be created for each user in the organization
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE,
                AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_PRODUCTION_MANAGER,
                AuthenticationController.ROLE_PRODUCTION_MANAGER);

        authenticationRepository.addUserWithRole("Main Administrator", "admin@this.app", "admin",
                AuthenticationController.ROLE_ADMIN);

        authenticationRepository.addUserWithRole("Employee", "employee@this.app", "pwd",
                AuthenticationController.ROLE_EMPLOYEE);

        authenticationRepository.addUserWithRole("Production Manager", PROD_MANAGER_EMAIL.trim(), PROD_MANAGER_PASSWORD.trim(),
                AuthenticationController.ROLE_PRODUCTION_MANAGER);
    }
}