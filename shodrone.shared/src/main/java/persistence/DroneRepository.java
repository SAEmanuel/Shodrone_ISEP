package persistence;

import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.DroneRemovalLog;

import java.util.List;
import java.util.Optional;

public interface DroneRepository {

    Optional<Drone> save(Drone drone);

    Optional<Drone> addExistingDroneInventory(Drone drone);

    Optional<Drone> findByDroneSN(String SN);

    Optional<List<Drone>> findByDroneModel(DroneModel droneModel);

    Optional<Drone> removeDrone(Drone drone, DroneRemovalLog log);

    List<Drone> findAll();
}
