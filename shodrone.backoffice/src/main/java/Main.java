import persistence.RepositoryProvider;
import ui.menu.StartupMessageBackofficeUI;
import ui.menu.MainMenuUI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Bootstrap bootstrap = new Bootstrap();
        boolean typePersistence = true;

        try {
            typePersistence = StartupMessageBackofficeUI.display();
            RepositoryProvider.setUseInMemory(typePersistence);
            bootstrap.run();
            MainMenuUI menu = new MainMenuUI();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}