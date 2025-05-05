package persistence.interfaces;

import domain.entity.FigureCategory;
import domain.entity.DroneModel;

import java.util.List;
import java.util.Optional;

public interface DroneModelRepository {

    Optional<DroneModel> save(DroneModel droneModelID);

    Optional<DroneModel> findByDroneModelID(String ID);

    List<DroneModel> findAll();
}
