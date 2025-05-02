package ui.menu;

import ui.authz.AuthenticationUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.*;


public class MainMenuUI implements Runnable {

    public MainMenuUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Login", new AuthenticationUI()));
        options.add(new MenuItem("Sign-Up", new SignUpUI()));
        options.add(new MenuItem("Development Team", new DevTeamUI()));

        int option;
        do {
            System.out.println("\n\n╔════════════════════════════════════════╗");
            option = Utils.showAndSelectIndex(options, "║"+ANSI_BRIGHT_WHITE+"               MAIN MENU               "+ANSI_RESET +" ║");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}