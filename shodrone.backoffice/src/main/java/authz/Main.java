package authz;

import authz.UI.MainMenuUI;

import java.io.IOException;

import static authz.more.ColorfulOutput.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();
        System.out.printf("%n%sDo login with:%n • %-9s -> %s%n • %-9s -> %s%s", ANSI_BRIGHT_BLACK, "User", "prodm@this.app", "Password", "manager", ANSI_RESET);


        try {
            MainMenuUI menu = new MainMenuUI();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}