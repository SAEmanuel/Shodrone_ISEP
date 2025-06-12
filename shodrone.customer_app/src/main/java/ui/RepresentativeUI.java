package ui;

import controller.network.AuthenticationController;
import lombok.Getter;
import ui.proposals.AnalyseProposalUI;
import ui.show.CheckShowDatesUI;
import ui.show.GetShowInfoUI;
import ui.menu.ShowTextUI;
import utils.MenuItem;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;

/**
 * UI class that presents the main menu for a Customer Representative.
 * <p>
 * This interface allows representatives to:
 * <ul>
 *     <li>Analyse proposals</li>
 *     <li>Consult show agenda (placeholder)</li>
 *     <li>Check the dates of planned shows</li>
 *     <li>Get detailed information of registered shows</li>
 * </ul>
 *
 * The menu is built using a list of {@link MenuItem} objects,
 * each one invoking a different use case UI when selected.
 * </p>
 *
 * <p>Loop continues until the user exits (selects -1).</p>
 *
 * @author
 */
public class RepresentativeUI implements Runnable {

    /**
     * Controller responsible for managing the authentication context.
     * Used to propagate the authenticated session to other UIs.
     */
    @Getter
    private final AuthenticationController authController;

    /**
     * Constructor that sets the authentication controller.
     *
     * @param authController the controller with login session context.
     */
    public RepresentativeUI(AuthenticationController authController) {
        this.authController = authController;
    }

    /**
     * Displays the main menu for the Representative user.
     * <p>
     * Uses {@link Utils#showAndSelectIndex(List, String)} to render
     * a dynamic menu with the available functionalities.
     * </p>
     *
     * <p>Each menu item is tied to a UI runnable class that will
     * handle the specific interaction. The menu loops until
     * the user exits by selecting an invalid option (-1).</p>
     */
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();

        options.add(new MenuItem("Analyse Proposals", new AnalyseProposalUI(getAuthController())));
        options.add(new MenuItem("Consult Shows Agenda", new ShowTextUI("Not implemented yet.")));
        options.add(new MenuItem("Check Show's Dates", new CheckShowDatesUI(getAuthController())));
        options.add(new MenuItem("Get Show Info", new GetShowInfoUI(getAuthController())));

        int option = 0;
        do {
            String menu = "\n╔═════════" + ANSI_BRIGHT_WHITE + " REPRESENTATIVE MENU " + ANSI_RESET + "══════════╗";
            option = Utils.showAndSelectIndex(options, menu);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
