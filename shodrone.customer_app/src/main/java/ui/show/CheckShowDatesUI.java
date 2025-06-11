package ui.show;

import controller.network.AuthenticationController;
import controller.show.CheckShowDatesController;
import domain.valueObjects.NIF;
import network.ShowDTO;
import utils.Utils;

import java.util.List;
import java.util.Optional;

import static more.ColorfulOutput.*;
import static more.TextEffects.*;

public class CheckShowDatesUI implements Runnable {
    private final CheckShowDatesController controller;

    public CheckShowDatesUI(AuthenticationController authController) {
        this.controller = new CheckShowDatesController(authController);
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("CHECK SHOW DATES");

        try {
            String email = AuthenticationController.getEmailLogin();
            System.out.printf("%s%s• Email:%s      %s\n", BOLD, ANSI_BRIGHT_BLUE, ANSI_RESET, email);

            NIF customerFound = controller.getCustomerNIFOfTheRepresentativeAssociated(email);
            System.out.printf("%s%s• Customer:%s   %s\n", BOLD, ANSI_BRIGHT_BLUE, ANSI_RESET, customerFound);
            Optional<List<ShowDTO>> listOfShowsOpt = controller.getShowsForCustomer(customerFound);

            if (listOfShowsOpt.isEmpty() || listOfShowsOpt.get().isEmpty()) {
                Utils.printFailMessage("\n✖ No shows found for the associated customer to representative with Email [" + email + "].");
            } else {
                List<ShowDTO> listOfShows = listOfShowsOpt.get();

                List<ShowDTO> filteredShows = listOfShows.stream()
                        .filter(show -> show.getStatus().equalsIgnoreCase("PLANNED"))
                        .filter(show -> {
                            try {
                                return java.time.LocalDateTime.parse(show.getShowDate(),
                                                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                                        .isAfter(java.time.LocalDateTime.now());
                            } catch (Exception e) {
                                return false;
                            }
                        })
                        .sorted((s1, s2) -> s2.getShowDate().compareTo(s1.getShowDate()))
                        .toList();

                if (filteredShows.isEmpty()) {
                    Utils.printFailMessage("\n✖ No PLANNED shows with a future date found.");
                    return;
                }

                System.out.printf("%s%s• Nº of Planned Future Shows:%s %d\n", BOLD, ANSI_BRIGHT_BLUE, ANSI_RESET, filteredShows.size());

                System.out.printf("\n\n%s        🧾 UPCOMING PLANNED SHOWS (Most Recent First)        %s", ANSI_MEDIUM_SPRING_GREEN, ANSI_RESET);
                System.out.printf("\n%s───────────────────────────────────────────────────────────────%s\n\n", ANSI_BRIGHT_BLACK, ANSI_RESET);

                int i = 1;
                for (ShowDTO show : filteredShows) {
                    System.out.printf("%s  • Show %02d:%s  ID: %d | Status: %s | Date: %s\n",
                            ANSI_BRIGHT_BLACK, i++, ANSI_RESET,
                            show.getShowID(),
                            show.getStatus(),
                            show.getShowDate()
                    );
                }

                Utils.printSuccessMessage("\n✔ Filtered future planned shows listed successfully!");
            }

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }
    }


}
