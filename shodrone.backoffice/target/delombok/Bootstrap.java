import controller.AuthenticationController;
import persistence.inmemory.InMemoryAuthenticationRepository;
import persistence.inmemory.Repositories;

public class Bootstrap implements Runnable {

    private static final String PROD_MANAGER_EMAIL = "prodm@this.app";
    private static final String PROD_MANAGER_PASSWORD = "manager";


    //Add some task categories to the repository as bootstrap
    public void run() {
        addUsers();
    }

    //------------------------------ Serializable ----------------------------------------------
    protected static void inputAppInformation() {
        //Serialization serialization = new Serialization();
        //serialization.serializeRepositoriesInput();
    }


    //add users to the repository
    private void addUsers() {
        //TODO: add Authentication users here: should be created for each user in the organization
        InMemoryAuthenticationRepository inMemoryAuthenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_CRM_MANAGER, AuthenticationController.ROLE_CRM_MANAGER);
        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_CRM_COLLABORATOR, AuthenticationController.ROLE_CRM_COLLABORATOR);
        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_SHOW_DESIGNER, AuthenticationController.ROLE_SHOW_DESIGNER);
        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE, AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_DRONE_TECH, AuthenticationController.ROLE_DRONE_TECH);

        inMemoryAuthenticationRepository.addUserWithRole("Administrator x"          , "admin@this.app"              , "admin" , AuthenticationController.ROLE_ADMIN);
        inMemoryAuthenticationRepository.addUserWithRole("CRM Manager x"            , "crm_manager@this.app"        , "crmman", AuthenticationController.ROLE_CRM_MANAGER);
        inMemoryAuthenticationRepository.addUserWithRole("CRM Collaborator x"       , "crm_collaborator@this.app"   , "crmcol", AuthenticationController.ROLE_CRM_COLLABORATOR);
        inMemoryAuthenticationRepository.addUserWithRole("Show Designer x"          , "show_designer@this.app"      , "shwdsg", AuthenticationController.ROLE_SHOW_DESIGNER);
        inMemoryAuthenticationRepository.addUserWithRole("Representative x"         , "representative@this.app"     , "repre" , AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        inMemoryAuthenticationRepository.addUserWithRole("Drone Technician x"       , "dronetech@this.app"          , "drone" , AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        inMemoryAuthenticationRepository.addUserWithRole("DroSDSDn x"               , "xu@this.app"                 , "drone" , AuthenticationController.ROLE_CRM_COLLABORATOR);

    }
}