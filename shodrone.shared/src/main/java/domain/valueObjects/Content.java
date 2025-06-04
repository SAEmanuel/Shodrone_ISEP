package domain.valueObjects;


import domain.entity.Costumer;
import domain.entity.DroneModel;
import domain.entity.Figure;
import jakarta.persistence.Embeddable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Embeddable
public class Content {

    Costumer customer;
    LocalDateTime showDate;
    Location showLocation;
    Duration showDuration;
    Map<Integer, Figure> figures;
    Map<DroneModel, Integer> droneModels;
    Video video;

    String text;

    public Content(String text) {
        this.text = text;
        customer = null;
        showDate = null;
        showLocation = null;
        showDuration = null;
        figures = new HashMap<>();
        droneModels = new HashMap<>();
        video = null;
    }

    public Content() {
    }

    public Costumer customer() {
        return this.customer;
    }

    public LocalDateTime showDate() {
        return this.showDate;
    }

    public Location showLocation() {
        return showLocation;
    }

    public Duration showDuration() {
        return showDuration;
    }

    public Map<Integer, Figure> figures() {
        return figures;
    }

    public Map<DroneModel, Integer> droneModels( ) {
        return droneModels;
    }

    public Video video() {
        return video;
    }

    public String text() {
        return text;
    }

    public void changeCustomer(Costumer customer) {
        this.customer = customer;
    }

    public void changeShowDate(LocalDateTime showDate) {
        this.showDate = showDate;
    }

    public void changeShowLocation(Location showLocation) {
        this.showLocation = showLocation;
    }

    public void changeShowDuration(Duration showDuration) {
        this.showDuration = showDuration;
    }

    public void changeFigures(Map<Integer, Figure> figures) {
        this.figures = figures;
    }

    public void changeDroneModels(Map<DroneModel, Integer> droneModels) {
        this.droneModels = droneModels;
    }

    public void changeVideo(Video video) {
        this.video = video;
    }

    public void changeText(String text) {
        this.text = text;
    }
}
