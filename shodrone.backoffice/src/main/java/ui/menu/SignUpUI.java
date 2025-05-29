package ui.menu;

import constants.Roles;
import controller.authz.AuthenticationController;
import ui.users.RegisterUserUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;

public class SignUpUI implements Runnable {

    @Override
    public void run() {

        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register as CRM Manager",new RegisterUserUI(Roles.ROLE_CRM_MANAGER)));
        options.add(new MenuItem("Register as CRM Collaborator", new RegisterUserUI(Roles.ROLE_CRM_COLLABORATOR)));
        options.add(new MenuItem("Register as Show Designer", new RegisterUserUI(Roles.ROLE_SHOW_DESIGNER)));
        options.add(new MenuItem("Register as Drone Technician", new RegisterUserUI(Roles.ROLE_DRONE_TECH)));

        int option = 0;
        do {
            System.out.println("\n\n╔════════════════════════════════════════╗");
            option = Utils.showAndSelectIndex(options, "║"+ANSI_BRIGHT_WHITE+"               Register                "+ANSI_RESET +" ║");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
