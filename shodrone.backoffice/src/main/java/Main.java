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


//        Name name = Name.valueOf("Jorge", "Ubaldo");
//        EmailAddress emailAddress = EmailAddress.valueOf("jorgeubaldorf@gmail.com");
//        PhoneNumber phoneNumber = new PhoneNumber("912861312");
//        NIF nif = new NIF("123456789");
//        Address address = new Address("Rua brigadeiro","Porto","4440-778","Portugal");
//
//        Customer customer1 = new Customer(name,emailAddress,phoneNumber,nif,address);
//
CostumerJPAImpl costumerJPAImpl = new CostumerJPAImpl();
//        costumerJPAImpl.add(customer1);
        System.out.println(costumerJPAImpl.findAll().size());

        try {
            StartupMessageBackofficeUI.display();
            MainMenuUI menu = new MainMenuUI();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}