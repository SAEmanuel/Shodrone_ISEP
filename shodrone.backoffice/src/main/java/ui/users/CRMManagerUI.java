package ui.users;

import ui.showrequest.ListShowRequestByCostumerUI;
import utils.MenuItem;
import ui.menu.ShowTextUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;

/**
 * User interface for the CRM Manager role.
 * <p>
 * Provides access to functionalities related to customer relationship management,
 * such as viewing client show requests and managing client-related data.
 * Currently, only a subset of options are implemented.
 */
public class CRMManagerUI implements Runnable {

    /**
     * Constructs a new instance of the CRMManagerUI.
     */
    public CRMManagerUI() {
    }

    /**
     * Displays the CRM Manager menu and handles user interaction.
     * <p>
     * The menu allows the user to:
     * <ul>
     *     <li>List all show requests of clients</li>
     *     <li>Change customer status (not implemented yet)</li>
     *     <li>Manage proposal templates (not implemented yet)</li>
     *     <li>Manage clients (not implemented yet)</li>
     * </ul>
     * The menu loop continues until the user selects the exit option.
     */
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("List Show Request of Clients", new ListShowRequestByCostumerUI()));
        options.add(new MenuItem("Change Costumer Status", new ShowTextUI("Not implemented yet.")));
        options.add(new MenuItem("Manage Proposal Templates", new ShowTextUI("Not implemented yet.")));
        options.add(new MenuItem("Manage Clients", new ShowTextUI("Not implemented yet.")));

        int option;
        do {
            String menu = "\n╔══════════ " + ANSI_BRIGHT_WHITE + " CRM MANAGER MENU " + ANSI_RESET + " ══════════╗";
            option = Utils.showAndSelectIndex(options, menu);

            if (option >= 0 && option < options.size()) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
