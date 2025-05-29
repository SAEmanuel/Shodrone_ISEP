import ui.CustomerAppUI;
import ui.StartupMessageCustomerAppUI;

public class Main {

    public static void main(String[] args) {
        CustomerAppUI customerAppUI = new CustomerAppUI();
        StartupMessageCustomerAppUI.display();
        customerAppUI.run();

    }
}
