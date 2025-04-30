import domain.entity.Customer;
import domain.valueObjects.Address;
import domain.valueObjects.NIF;
import domain.valueObjects.PhoneNumber;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import persistence.jpa.JPAImpl.CostumerJPAImpl;
import persistence.jpa.JPAImpl.ShowRequestJPAImpl;
import ui.menu.StartupMessageBackofficeUI;
import ui.menu.MainMenuUI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();

        try {
            StartupMessageBackofficeUI.display();
            MainMenuUI menu = new MainMenuUI();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}