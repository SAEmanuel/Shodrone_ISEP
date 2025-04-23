package authz.UI;

import authz.UI.menu.MenuItem;
import authz.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static authz.more.ColorfulOutput.*;

/**
 * @author Paulo Maio pam@isep.ipp.pt
 */
public class MainMenuUI implements Runnable {

    public MainMenuUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Login", new AuthenticationUI()));
        options.add(new MenuItem("Development Team", new DevTeamUI()));

        int option = 0;
        do {
            System.out.println("\n\n╔════════════════════════════════════════╗");
            option = Utils.showAndSelectIndex(options, "║"+ANSI_BRIGHT_WHITE+"               MAIN MENU               "+ANSI_RESET +" ║");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}