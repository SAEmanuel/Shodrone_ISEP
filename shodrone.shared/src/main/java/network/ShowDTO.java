package network;

import domain.entity.Figure;
import domain.entity.Show;
import domain.valueObjects.Location;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShowDTO {

    private Long showID;
    private Long showProposalAcceptedID;
    private List<Long> sequenceFigures;
    private Location location;
    private String showDate;
    private int numberOfDrones;
    private String showDuration;
    private String status;
    private Long customerID;

    public ShowDTO() {}

    public ShowDTO(Long showID, Long showProposalID, List<Long> listFigure, Location location,
                   String showDate, int numberOfDrones, String showDuration, String status, Long customerID) {
        this.showID = showID;
        this.showProposalAcceptedID = showProposalID;
        this.sequenceFigures = listFigure;
        this.location = location;
        this.showDate = showDate;
        this.numberOfDrones = numberOfDrones;
        this.showDuration = showDuration;
        this.status = status;
        this.customerID = customerID;
    }

    public static ShowDTO fromEntity(Show show) {
        List<Long> listFigure = new ArrayList<>();
        for (Figure figure : show.getSequenceFigures()) {
            listFigure.add(figure.identity());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = show.getShowDate().format(formatter);
        String formattedDuration = show.getShowDuration().toString();

        return new ShowDTO(
                show.identity(),
                show.getShowProposalAcceptedID().identity(),
                listFigure,
                show.getLocation(),
                formattedDate,
                show.getNumberOfDrones(),
                formattedDuration,
                show.getStatus().name(),
                show.getCustomerID()
        );
    }

    // Getters (optional if using libraries like Jackson or Gson for JSON serialization)
    public Long getShowID() {
        return showID;
    }

    public Long getShowProposalAcceptedID() {
        return showProposalAcceptedID;
    }

    public List<Long> getSequenceFigures() {
        return sequenceFigures;
    }

    public Location getLocation() {
        return location;
    }

    public String getShowDate() {
        return showDate;
    }

    public int getNumberOfDrones() {
        return numberOfDrones;
    }

    public String getShowDuration() {
        return showDuration;
    }

    public String getStatus() {
        return status;
    }

    public Long getCustomerID() {
        return customerID;
    }

    @Override
    public String toString() {
        return String.format(
                "🧾 ID: [%d], Proposal ID: [%d], 📅 Date: %s, ⏱️ Duration: %s, 🚁 Drones: %d, 📍 Location: %s, " +
                        "🗿 Figures: %s, 💬 Status: %s, 👤 Customer ID: %d",
                showID,
                showProposalAcceptedID,
                showDate,
                showDuration,
                numberOfDrones,
                location,
                sequenceFigures,
                status,
                customerID
        );
    }


}
