package ui.show;

import controller.network.AuthenticationController;
import controller.show.GetShowInfoController;
import domain.valueObjects.NIF;
import network.ShowDTO;
import utils.Utils;

import java.util.List;
import java.util.Optional;

import static more.ColorfulOutput.*;
import static more.ColorfulOutput.ANSI_BRIGHT_BLACK;
import static more.ColorfulOutput.ANSI_RESET;
import static more.TextEffects.BOLD;
import static more.TextEffects.ITALIC;
import static utils.Utils.*;

public class GetShowInfoUI implements Runnable{
    private final GetShowInfoController controller;
    private static final int EXIT = -1;
    final static String COLOR_OPTIONS = ANSI_BRIGHT_BLACK;
    final static int STEP = 10;

    public GetShowInfoUI(AuthenticationController authController) {
        this.controller = new GetShowInfoController(authController);
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("GET SHOW INFO");

        try {
            String email = AuthenticationController.getEmailLogin();
            System.out.printf("%s%s• Email:%s      %s\n",BOLD,ANSI_BRIGHT_BLUE,ANSI_RESET,email);

            NIF customerFound = controller.getCustomerNIFOfTheRepresentativeAssociated(email);
            System.out.printf("%s%s• Customer:%s   %s\n",BOLD,ANSI_BRIGHT_BLUE,ANSI_RESET,customerFound);
            Optional<List<ShowDTO>> listOfShows = controller.getShowsForCustomer(customerFound);

            if(listOfShows.isEmpty()){
                Utils.printFailMessage("\n✖ No shows found for the associated customer to representative with Email [" + email + "].");
            }else {
                int size = listOfShows.get().size();
                System.out.printf("%s%s• Nº of Show:%s %s\n", BOLD, ANSI_BRIGHT_BLUE, ANSI_RESET, size);
                Utils.dropLines(3);

                int index = 0;
                Optional<ShowDTO> show = null;
                if (listOfShows.isPresent() && !listOfShows.get().isEmpty()) {

                    index = showAndSelectIndexPartially(listOfShows.get(), "Show's");

                    if (index == EXIT) {
                        Utils.printFailMessage("No show selected...");
                        return;
                    }

                    show = Optional.ofNullable(listOfShows.get().get(index));
                }
                Utils.dropLines(10);
                System.out.printf("\n\n%s         🧾 SHOW'S INFORMATION SUMMARY                   %s", ANSI_MEDIUM_SPRING_GREEN, ANSI_RESET);
                System.out.printf("\n%s──────────────────────────────────────────────────%s\n\n", ANSI_BRIGHT_BLACK, ANSI_RESET);
                System.out.printf(
                        "%s  • Show Nº%s%-3s%s%n" +
                                "    🧾 ID: [%d]%n" +
                                "    Proposal ID: [%d]%n" +
                                "    📅 Date: %s%n" +
                                "    ⏱️ Duration: %s%n" +
                                "    🚁 Drones: %d%n" +
                                "    📍 Location: %s%n" +
                                "    💬 Status: %s%n" +
                                "    👤 Customer ID: %d%n" +
                                "    🗿 Figures: %s%n",
                        ANSI_BRIGHT_BLACK, index, ":", ANSI_RESET,
                        show.get().getShowID(),
                        show.get().getShowProposalAcceptedID(),
                        show.get().getShowDate(),
                        show.get().getShowDuration(),
                        show.get().getNumberOfDrones(),
                        show.get().getLocation(),
                        show.get().getStatus(),
                        show.get().getCustomerID(),
                        show.get().getSequenceFigures()
                );

                Utils.printSuccessMessage("\n✔ Check Show Dates for Customer Successfully Loaded!");
                Utils.waitForUser();
            }

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }
    }

    static public int showAndSelectIndexPartially(List<ShowDTO> list, String header) {
        int total = list.size();
        int startIndex = 0;
        int cycle = 0;

        while (startIndex < total) {
            int endIndex = Math.min(startIndex + STEP, total);
            List<ShowDTO> sublist = list.subList(startIndex, endIndex);

            showListCustom(sublist, header + " (Showing " + (startIndex + 1) + " to " + endIndex + " of " + total + ")");

            startIndex += STEP;

            if (startIndex < total) {
                boolean seeMore = Utils.confirm("Do you want to see more items? (y/n)");
                if (seeMore) {
                    cycle++;
                    continue;
                }
            }

            return selectsIndex(list, cycle);
        }

        return -1;
    }

    static public void showListCustom(List<ShowDTO> list, String header) {
        System.out.println(ANSI_BRIGHT_BLACK + ITALIC + "• ".concat(header).concat(":") + ANSI_RESET);
        int index = 0;
        for (ShowDTO show : list) {
            index++;
            System.out.printf("    %s(%d)%2s -  %-28s%n", COLOR_OPTIONS, index, ANSI_RESET, show.getShowDate());
        }
        System.out.printf("    %s(0)%2s -  %-20s%n", COLOR_OPTIONS, ANSI_RESET, "Cancel");
    }
}
