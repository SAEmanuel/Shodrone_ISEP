package persistence.interfaces;

import domain.entity.Drone;
import domain.valueObjects.DroneRemovalLog;

import java.util.List;
import java.util.Optional;

public interface DroneRepository {

    Optional<Drone> save(Drone drone);

    Optional<Drone> findByDroneSN(String SN);

    Optional<Drone> removeDrone(Drone drone, DroneRemovalLog log);

    List<Drone> findAll();
}
