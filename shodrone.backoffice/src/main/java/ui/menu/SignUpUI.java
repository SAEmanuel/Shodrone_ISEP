package ui.menu;

import ui.authz.AuthenticationUI;
import ui.authz.RegisterShowDesignerUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;

public class SignUpUI implements Runnable {

    @Override
    public void run() {

        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register as CRM Manager", new ShowTextUI("Not implemented yet")));
        options.add(new MenuItem("Register as CRM Collaborator", new ShowTextUI("Not implemented yet")));
        options.add(new MenuItem("Register as Show Designer", new RegisterShowDesignerUI()));
        options.add(new MenuItem("Register as Drone Technician", new ShowTextUI("Not implemented yet")));
        options.add(new MenuItem("Register as Costumer", new ShowTextUI("Not implemented yet")));

        int option = 0;
        do {
            System.out.println("\n\n╔════════════════════════════════════════╗");
            option = Utils.showAndSelectIndex(options, "║"+ANSI_BRIGHT_WHITE+"               SIGN UP                 "+ANSI_RESET +" ║");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
