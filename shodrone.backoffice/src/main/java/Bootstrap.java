import controller.AuthenticationController;
import persistence.RepositoryProvider;
import persistence.inmemory.InMemoryAuthenticationRepository;
import persistence.interfaces.AuthenticationRepository;
import persistence.jpa.JPAImpl.AuthenticationRepositoryJPAImpl;
import authz.*;


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


    private void addUsers() {
        AuthenticationRepository repo = RepositoryProvider.authenticationRepository();

        repo.addUserRole(AuthenticationController.ROLE_ADMIN, "Administrator");
        repo.addUserRole(AuthenticationController.ROLE_CRM_MANAGER, "CRM Manager");
        repo.addUserRole(AuthenticationController.ROLE_CRM_COLLABORATOR, "CRM Collaborator");
        repo.addUserRole(AuthenticationController.ROLE_SHOW_DESIGNER, "Show Designer");
        repo.addUserRole(AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE, "Customer Representative");
        repo.addUserRole(AuthenticationController.ROLE_DRONE_TECH, "Drone Technician");

        repo.addUserWithRole("Administrator x", "admin@this.app", "admin", AuthenticationController.ROLE_ADMIN);
        repo.addUserWithRole("CRM Manager x", "crm_manager@this.app", "crmman", AuthenticationController.ROLE_CRM_MANAGER);
        repo.addUserWithRole("CRM Collaborator x", "crm_collaborator@this.app", "crmcol", AuthenticationController.ROLE_CRM_COLLABORATOR);
        repo.addUserWithRole("Show Designer x", "show_designer@this.app", "shwdsg", AuthenticationController.ROLE_SHOW_DESIGNER);
        repo.addUserWithRole("Representative x", "representative@this.app", "repre", AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        repo.addUserWithRole("Drone Technician x", "dronetech@this.app", "drone", AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        repo.addUserWithRole("DroSDSDn x", "xu@this.app", "drone", AuthenticationController.ROLE_CRM_COLLABORATOR);
    }


}