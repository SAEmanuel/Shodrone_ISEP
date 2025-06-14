package ui.users;

import ui.figure.ListPublicFiguresUI;
import ui.figure.SearchFigureUI;
import ui.showproposal.*;
import ui.showrequest.EditShowRequestUI;
import ui.showrequest.ListShowRequestByCostumerUI;
import ui.customer.RegisterCustomerUI;
import ui.showrequest.RegisterShowRequestUI;
import utils.MenuItem;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;

/**
 * User interface for the CRM Collaborator role.
 * <p>
 * This menu provides options for CRM collaborators to interact with customer-related data,
 * including the ability to submit, view, and edit show requests.
 * </p>
 */
public class CRMCollaboratorUI implements Runnable {

    /**
     * Constructs a new instance of the CRMCollaboratorUI.
     */
    public CRMCollaboratorUI() {
    }

    /**
     * Displays the CRM Collaborator menu and handles user interaction.
     * <p>
     * The menu allows the user to:
     * <ul>
     *     <li>Submit a show request</li>
     *     <li>List show requests of clients</li>
     *     <li>Edit an existing show request</li>
     *     <li>Register a new customer</li>
     *     <li>List all active and public figures</li>
     *     <li>Search figures</li>
     * </ul>
     * The menu loop continues until the user selects the exit option.
     * </p>
     */
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Submit Show Request", new RegisterShowRequestUI()));
        options.add(new MenuItem("List Show Request of Clients", new ListShowRequestByCostumerUI()));
        options.add(new MenuItem("Edit Show Request", new EditShowRequestUI()));
        options.add(new MenuItem("Register customer", new RegisterCustomerUI()));
        options.add(new MenuItem("List All Public Figures", new ListPublicFiguresUI()) );
        options.add(new MenuItem("Search Figures", new SearchFigureUI()) );
        options.add(new MenuItem("Create Show Proposal", new CreateShowProposalUI()));
        options.add(new MenuItem("Add Figures to Show Proposal", new AddFiguresToProposalUI()));
        options.add(new MenuItem("Add Video To Show Proposal", new AddVideoOfSimulationToTheProposalUI()));
        options.add(new MenuItem("Add Drones to Show Proposal", new AddDronesShowProposalUI()));
        options.add(new MenuItem("Accept show proposal", new AcceptShowProposalUI()));

        int option;
        do {
            String menu = "\n╔═══════════" + ANSI_BRIGHT_WHITE + " CRM COLLABORATOR MENU " + ANSI_RESET + "═══════════╗";
            option = Utils.showAndSelectIndexv2(options, menu);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
