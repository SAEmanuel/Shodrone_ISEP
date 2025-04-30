import controller.AuthenticationController;
import persistence.inmemory.InMemoryAuthenticationRepository;
import persistence.inmemory.Repositories;
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


    //add users to the repository
//    private void addUsers() {
//        //TODO: add Authentication users here: should be created for each user in the organization
//        InMemoryAuthenticationRepository inMemoryAuthenticationRepository = Repositories.getInstance().getAuthenticationRepository();
//        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
//        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_CRM_MANAGER, AuthenticationController.ROLE_CRM_MANAGER);
//        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_CRM_COLLABORATOR, AuthenticationController.ROLE_CRM_COLLABORATOR);
//        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_SHOW_DESIGNER, AuthenticationController.ROLE_SHOW_DESIGNER);
//        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE, AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
//        inMemoryAuthenticationRepository.addUserRole(AuthenticationController.ROLE_DRONE_TECH, AuthenticationController.ROLE_DRONE_TECH);
//
//        inMemoryAuthenticationRepository.addUserWithRole("Administrator x"          , "admin@this.app"              , "admin" , AuthenticationController.ROLE_ADMIN);
//        inMemoryAuthenticationRepository.addUserWithRole("CRM Manager x"            , "crm_manager@this.app"        , "crmman", AuthenticationController.ROLE_CRM_MANAGER);
//        inMemoryAuthenticationRepository.addUserWithRole("CRM Collaborator x"       , "crm_collaborator@this.app"   , "crmcol", AuthenticationController.ROLE_CRM_COLLABORATOR);
//        inMemoryAuthenticationRepository.addUserWithRole("Show Designer x"          , "show_designer@this.app"      , "shwdsg", AuthenticationController.ROLE_SHOW_DESIGNER);
//        inMemoryAuthenticationRepository.addUserWithRole("Representative x"         , "representative@this.app"     , "repre" , AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
//        inMemoryAuthenticationRepository.addUserWithRole("Drone Technician x"       , "dronetech@this.app"          , "drone" , AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
//        inMemoryAuthenticationRepository.addUserWithRole("DroSDSDn x"               , "xu@this.app"                 , "drone" , AuthenticationController.ROLE_CRM_COLLABORATOR);
//
//    }
    private void addUsers() {
        AuthenticationRepositoryJPAImpl authRepo =
                (AuthenticationRepositoryJPAImpl) Repositories.getInstance().getJpaAuthenticationRepository();

        // Criar roles (verificando antes se já existem)
        createRoleIfNotExists(authRepo, AuthenticationController.ROLE_ADMIN, "Administrator");
        createRoleIfNotExists(authRepo, AuthenticationController.ROLE_CRM_MANAGER, "CRM Manager");
        createRoleIfNotExists(authRepo, AuthenticationController.ROLE_CRM_COLLABORATOR, "CRM Collaborator");
        createRoleIfNotExists(authRepo, AuthenticationController.ROLE_SHOW_DESIGNER, "Show Designer");
        createRoleIfNotExists(authRepo, AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE, "Customer Representative");
        createRoleIfNotExists(authRepo, AuthenticationController.ROLE_DRONE_TECH, "Drone Technician");

        // Criar users com as roles respetivas
        saveUserWithRole(authRepo, "Administrator x", "admin@this.app", "admin", AuthenticationController.ROLE_ADMIN);
        saveUserWithRole(authRepo, "CRM Manager x", "crm_manager@this.app", "crmman", AuthenticationController.ROLE_CRM_MANAGER);
        saveUserWithRole(authRepo, "CRM Collaborator x", "crm_collaborator@this.app", "crmcol", AuthenticationController.ROLE_CRM_COLLABORATOR);
        saveUserWithRole(authRepo, "Show Designer x", "show_designer@this.app", "shwdsg", AuthenticationController.ROLE_SHOW_DESIGNER);
        saveUserWithRole(authRepo, "Representative x", "representative@this.app", "repre", AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        saveUserWithRole(authRepo, "Drone Technician x", "dronetech@this.app", "drone", AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        saveUserWithRole(authRepo, "DroSDSDn x", "xu@this.app", "drone", AuthenticationController.ROLE_CRM_COLLABORATOR);
    }

    private void saveUserWithRole(AuthenticationRepositoryJPAImpl repo, String name, String email, String password, String roleId) {
        Email userEmail = new Email(email);

        if (repo.findById(userEmail) != null) {
            //System.out.println("⚠️ Utilizador já existe: " + email);
            return;
        }

        UserRole role = repo.getRoleRepository().findById(roleId)
                .orElseThrow(() -> new IllegalStateException("❌ Role not found: " + roleId));

        Password userPassword = new Password(password);
        User user = new User(userEmail, userPassword, name);

        user.addRole(role);

        repo.saveUser(user);
    }


    private void createRoleIfNotExists(AuthenticationRepositoryJPAImpl repo, String roleId, String description) {
        if (repo.getRoleRepository().findById(roleId).isEmpty()) {
            repo.saveRole(new UserRole(roleId, description));
        }
    }


}