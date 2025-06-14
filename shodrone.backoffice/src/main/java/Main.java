import persistence.RepositoryProvider;
import ui.menu.StartupMessageBackofficeUI;
import ui.menu.MainMenuUI;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void suppressUnwantedWarnings() {
        Logger rootLogger = java.util.logging.LogManager.getLogManager().getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }
        rootLogger.addHandler(new Handler() {
            @Override
            public void publish(java.util.logging.LogRecord record) {
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
        });

        Logger platformLogger = Logger.getLogger("com.sun.javafx.application.PlatformImpl");
        platformLogger.setLevel(Level.OFF);
        platformLogger.setUseParentHandlers(false);
    }

    public static void main(String[] args) throws IOException {
        suppressUnwantedWarnings();

        boolean typePersistence = true;

        try {
            typePersistence = StartupMessageBackofficeUI.display();
            RepositoryProvider.setUseInMemory(typePersistence);
            RepositoryProvider.initializeAuditLogger(RepositoryProvider.isInMemory());

            Bootstrap bootstrap = new Bootstrap(typePersistence);
            bootstrap.run();
            if (!typePersistence) {
                new Thread(() -> {
                    SimulationTriggerLoop loop = new SimulationTriggerLoop();
                    loop.run();
                }).start();
            }

            MainMenuUI menu = new MainMenuUI();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}