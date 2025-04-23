import controller.AuthenticationController;
import repositories.*;

public class Bootstrap implements Runnable {

    private static final String PROD_MANAGER_EMAIL = "prodm@this.app";
    private static final String PROD_MANAGER_PASSWORD = "manager";


    //Add some task categories to the repository as bootstrap
    public void run() {
        addUsers();
    }



    //add users to the repository
    private void addUsers() {
        //TODO: add Authentication users here: should be created for each user in the organization
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_CRM_MANAGER, AuthenticationController.ROLE_CRM_MANAGER);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_CRM_COLLABORATOR, AuthenticationController.ROLE_CRM_COLLABORATOR);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_SHOW_DESIGNER, AuthenticationController.ROLE_SHOW_DESIGNER);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE, AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);

        authenticationRepository.addUserWithRole("Administrator x"          , "admin@this.app"              , "admin" , AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserWithRole("CRM Manager x"            , "crm_manager@this.app"        , "crmman", AuthenticationController.ROLE_CRM_MANAGER);
        authenticationRepository.addUserWithRole("CRM Collaborator x"       , "crm_collaborator@this.app"   , "crmcol", AuthenticationController.ROLE_CRM_COLLABORATOR);
        authenticationRepository.addUserWithRole("Show Designer x"          , "show_designer@this.app"      , "shwdsg", AuthenticationController.ROLE_SHOW_DESIGNER);
        authenticationRepository.addUserWithRole("Representative x"         , "representative@this.app"     , "repre" , AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);

    }
}