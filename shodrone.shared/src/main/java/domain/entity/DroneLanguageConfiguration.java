package domain.entity;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DroneLanguageConfiguration implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private DroneModel droneModel;

    @Column(nullable = false)
    private String droneLanguage;

    public DroneLanguageConfiguration(DroneModel droneModel, String droneLanguage) {
        this.droneModel = droneModel;
        this.droneLanguage = droneLanguage;
    }

    @Override
    public Long identity() {
        return id;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof DroneLanguageConfiguration)) {
            return false;
        }
        DroneLanguageConfiguration that = (DroneLanguageConfiguration) other;
        return this.id != null && this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return "DroneLanguageConfiguration{" +
                "id=" + id +
                ", droneModel=" + droneModel.identity() +
                ", droneLanguage='" + droneLanguage + '\'' +
                '}';
    }
}
