package persistence.jpa.JPAImpl;

import domain.entity.Drone;
import domain.valueObjects.DroneRemovalLog;
import domain.valueObjects.DroneStatus;
import persistence.interfaces.DroneRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

public class DroneJPAImpl extends JpaBaseRepository<Drone, Long> implements DroneRepository {

    public Optional<Drone> save(Drone drone) {
        Optional<Drone> checkExistence = findByDroneSN(drone.identity());
        if (checkExistence.isEmpty()) {
            Drone saved = this.add(drone);
            return Optional.ofNullable(saved);
        }
        return Optional.empty();
    }

    public Optional<Drone> findByDroneSN(String serialNumber) {
        List<Drone> result = entityManager()
                .createQuery("SELECT c FROM Drone c WHERE LOWER(c.serialNumber.serialNumber) = :serialNumber", Drone.class)
                .setParameter("serialNumber", serialNumber.toLowerCase())
                .getResultList();

        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result.get(0));
        }
    }

    @Override
    public Optional<Drone> removeDrone(Drone drone, DroneRemovalLog log) {
        if (drone == null || log == null) {
            return Optional.empty();
        }

        drone.changeDroneStatusTo(DroneStatus.UNAVAILABLE);
        drone.addDroneRemovalLog(log);

        Drone updatedDrone = update(drone);

        return Optional.of(updatedDrone);
    }

}
