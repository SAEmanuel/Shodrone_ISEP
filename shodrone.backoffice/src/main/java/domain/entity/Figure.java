package domain.entity;

import authz.Email;
import com.fasterxml.jackson.annotation.JsonProperty;
import domain.valueObjects.*;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "figure")
public class Figure implements AggregateRoot<Long>, Serializable {

    // Primary key for the Figure entity, auto-generated
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long figureId;

    @Embedded
    @Column(name = "Name", nullable = false)
    public Name name;

    @Embedded
    public Description description;

    @Column(name = "Version")
    private Long version;

    @ManyToOne //(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", nullable = false)
    private FigureCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FigureAvailability availability;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FigureStatus status;

    @Embedded
    public DSL dsl;

    @ManyToOne
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;



    protected Figure() {}

    /**
     * Constructs a new Figure entity with all required attributes.
     *
     * @param name          The name of the figure.
     * @param description   The description of the figure.
     * @param version       The version number of the figure.
     * @param category      The category to which the figure belongs.
     * @param availability  The availability status of the figure.
     * @param status        The current status of the figure.
     * @param dsl           The DSL associated with the figure.
     * @param costumer      The costumer associated with the figure.
     */
    public Figure(Name name, Description description,
                  Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, DSL dsl, Costumer costumer) {

        this.name = name;
        this.description = description;
        this.version = version;
        this.category = category;
        this.availability = availability;
        this.status = status;
        this.dsl = dsl;
        this.costumer = costumer;
    }


    /**
     * Returns the unique identifier of this Figure entity.
     *
     * @return The figure's identity (primary key).
     */
    @Override
    public Long identity() { return figureId; }

    public Name name() { return name; }

    public Description description() { return description; }

    public Long version() { return version; }

    public FigureCategory category() { return category; }

    public FigureAvailability availability() { return availability; }

    public FigureStatus status() { return status; }

    public DSL dsl() { return dsl; }

    public Costumer costumer() { return costumer; }


    /**
     * Updates the category of the figure.
     * @param category new category to assign
     */
    public void UpdateFigureCategory (FigureCategory category) { this.category = category; }

    /**
     * Updates the costumer (owner/client) of the figure.
     * @param costumer new costumer to assign
     */
    public void UpdateFigureCostumer (Costumer costumer) { this.costumer = costumer; }

    /**
     * Marks the figure as inactive (decommissioned).
     */
    public void decommissionFigureStatus() { this.status = FigureStatus.INACTIVE; }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Figure)) return false;
        Figure that = (Figure) other;
        return figureId.equals(that.figureId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Figure)) return false;
        Figure that = (Figure) o;
        return figureId.equals(that.figureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(figureId);
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %-3s | Name: %-20s | Description: %-30s | Version: %-6s | Category: %-20s | Status: %-6s | Availability: %-10s | DSL: %-20s | Costumer: %-20s",
                figureId != null ? figureId : "N/A",
                name != null ? name : "N/A",
                description != null ? description : "N/A",
                version != null ? version.toString() : "N/A",
                category != null ? category.identity() : "N/A",
                status != null ? status : "N/A",
                availability != null ? availability : "N/A",
                dsl != null ? dsl: "N/A",
                costumer != null ? costumer.name() : "N/A"
        );
    }
}
