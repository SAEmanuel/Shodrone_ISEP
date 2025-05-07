package persistence.interfaces;

import domain.entity.Drone;

import java.util.List;
import java.util.Optional;

public interface DroneRepository {

    Optional<Drone> save(Drone drone);

    Optional<Drone> findByDroneSN(String SN);

    List<Drone> findAll();
}
