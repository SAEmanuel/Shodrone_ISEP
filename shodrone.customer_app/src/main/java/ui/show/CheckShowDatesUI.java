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

/**
 * UI class responsible for displaying upcoming planned shows
 * for the customer associated with the currently logged-in representative.
 *
 * <p>This class retrieves the customer's NIF using the representative's email,
 * filters all PLANNED shows with a future date, and displays them in a sorted list.</p>
 *
 * <p>It interacts with the {@link CheckShowDatesController} to retrieve the necessary data.</p>
 *
 * @author
 */
public class CheckShowDatesUI implements Runnable {

    /** Controller responsible for retrieving customer NIF and their shows. */
    private final CheckShowDatesController controller;

    /**
     * Constructs the UI with the given authentication controller.
     *
     * @param authController the controller handling authentication and communication
     */
    public CheckShowDatesUI(AuthenticationController authController) {
        this.controller = new CheckShowDatesController(authController);
    }

    /**
     * Executes the UI logic to fetch and display planned future shows.
     * <p>Steps include:
     * <ol>
     *     <li>Fetching the representative's email from the login session</li>
     *     <li>Retrieving the associated customer NIF</li>
     *     <li>Fetching all shows of that customer</li>
     *     <li>Filtering shows that are PLANNED and scheduled for a future date</li>
     *     <li>Displaying them in a formatted list</li>
     * </ol>
     * If no shows are found or an error occurs, appropriate messages are printed.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("CHECK SHOW DATES");

        try {
            // Step 1: Get logged-in user email
            String email = AuthenticationController.getEmailLogin();
            System.out.printf("%s%s• Email:%s      %s\n", BOLD, ANSI_BRIGHT_BLUE, ANSI_RESET, email);

            // Step 2: Retrieve associated customer NIF
            NIF customerFound = controller.getCustomerNIFOfTheRepresentativeAssociated(email);
            System.out.printf("%s%s• Customer:%s   %s\n", BOLD, ANSI_BRIGHT_BLUE, ANSI_RESET, customerFound);

            // Step 3: Retrieve show list
            Optional<List<ShowDTO>> listOfShowsOpt = controller.getShowsForCustomer(customerFound);

            if (listOfShowsOpt.isEmpty() || listOfShowsOpt.get().isEmpty()) {
                Utils.printFailMessage("\n✖ No shows found for the associated customer to representative with Email [" + email + "].");
            } else {
                List<ShowDTO> listOfShows = listOfShowsOpt.get();

                // Step 4: Filter shows: must be PLANNED and scheduled for future
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
                        .sorted((s1, s2) -> s2.getShowDate().compareTo(s1.getShowDate())) // newest first
                        .toList();

                if (filteredShows.isEmpty()) {
                    Utils.printFailMessage("\n✖ No PLANNED shows with a future date found.");
                    return;
                }

                // Step 5: Display result
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
