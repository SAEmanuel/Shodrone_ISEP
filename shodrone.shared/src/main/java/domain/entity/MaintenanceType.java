package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import more.ColorfulOutput;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class MaintenanceType implements AggregateRoot<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private MaintenanceTypeName name;

    @Column(nullable = false)
    private Description description;

    protected MaintenanceType() {
        // For JPA
    }

    public MaintenanceType(MaintenanceTypeName name, Description description) {
        try {
            Preconditions.nonEmpty(name.name());
            Preconditions.nonEmpty(description.toString());

            this.name = name;
            this.description = description;

        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public MaintenanceType(MaintenanceTypeName name) {
        try {
            Preconditions.nonEmpty(name.name());

            this.name = name;
            this.description = new Description("Not Provided!");

        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public Long id() {
        return this.id;
    }

    public MaintenanceTypeName name() {
        return this.name;
    }

    public Description description() {
        return this.description;
    }

    @Override
    public String identity() {
        return this.name.name();
    }

    @Override
    public boolean hasIdentity(final String id) {
        return id.equalsIgnoreCase(this.name.name());
    }

    @Override
    public boolean sameAs(Object other) {
        final MaintenanceType maintenanceType = (MaintenanceType) other;
        return this.equals(maintenanceType) && name().equals(maintenanceType.name()) && description().equals(maintenanceType.description());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        String nameStr = name.name();
        String descStr = description.toString();

        String nameTrunc = nameStr.length() > 25 ? nameStr.substring(0, 22) + "..." : nameStr;
        String descTrunc = descStr.length() > 35 ? descStr.substring(0, 32) + "..." : descStr;

        return String.format(
                "%s%s%-4s%s: %s%-25s%s | " +
                        "%s%s%-11s%s: %s%-35s%s",
                ColorfulOutput.ANSI_ORANGE, ColorfulOutput.ANSI_BOLD, "Name", ColorfulOutput.ANSI_RESET,
                ColorfulOutput.ANSI_BRIGHT_WHITE, nameTrunc, ColorfulOutput.ANSI_RESET,

                ColorfulOutput.ANSI_ORANGE, ColorfulOutput.ANSI_BOLD, "Description", ColorfulOutput.ANSI_RESET,
                ColorfulOutput.ANSI_BRIGHT_WHITE, descTrunc, ColorfulOutput.ANSI_RESET
        );
    }

}
