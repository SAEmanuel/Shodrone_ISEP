import drone_language_validation.DroneGenericPlugin;
import persistence.RepositoryProvider;
import ui.menu.StartupMessageBackofficeUI;
import ui.menu.MainMenuUI;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

        List<String> dslLines = Arrays.asList(
                "DroneViado programming language version 0.6.66",
                "",
                "Types",
                "Point",
                "Vector",
                "LinearVelocity",
                "AngularVelocity",
                "Distance",
                "Time",
                "",
                "Variables",
                "Point p1 = (0, 1, 1.5);",
                "Vector dir = (0, 0, 1) - (0, 1, 0);",
                "LinearVelocity vel = 6.2;",
                "AngularVelocity angVel = PI / 10;",
                "Time hoverTime = 5000;",
                "Point path = ((0, 0, 0), (0, 10, 10), (10, 10, 10));",
                "Distance height = 15;",
                "",
                "Instructions",
                "takeOff(height, vel);",
                "move(p1, vel);",
                "move(dir, vel, hoverTime);",
                "movePath(path, vel);",
                "moveCircle(p1, PI, angVel);",
                "hoover(hoverTime);",
                "lightsOn(255,255,0);",
                "lightsOff();",
                "land(vel);"
        );

        DroneGenericPlugin drone = new DroneGenericPlugin();
        drone.validate(dslLines);

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