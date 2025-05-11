package ui.users;


import u.ListUserUI;
import ui.menu.ManageUserUI;
import ui.menu.MenuItem;
import ui.menu.SignUpUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;


public class AdminUI implements Runnable {
    public AdminUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Manage Users", new ManageUserUI()));
        options.add(new MenuItem("Register User", new SignUpUI()));
        options.add(new MenuItem("List Users", new ListUserUI()));

        int option = 0;
        do {
            String menu = "\n╔═════════════ "+ANSI_BRIGHT_WHITE+" ADMIN MENU "+ANSI_RESET+" ═════════════╗";
            option = Utils.showAndSelectIndex(options, menu);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}