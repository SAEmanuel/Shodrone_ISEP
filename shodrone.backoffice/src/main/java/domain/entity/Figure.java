package domain.entity;

import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.AggregateRoot;
import domain.valueObjects.Description;
import jakarta.persistence.*;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "figure")
public class Figure implements AggregateRoot<Long>, Serializable {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long figureId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Embedded
    private Description description;

    @Column(name = "Version")
    private Long version;

    @ManyToOne //(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    private FigureCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FigureAvailability availability;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FigureStatus status;

    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;

    protected Figure() {}
    
    public Figure(String name, Description description,
                  Long version, FigureCategory category, FigureAvailability availability, FigureStatus status,Costumer costumer) {

        this.name = name;
        this.description = description;
        this.version = version;
        this.category = category;
        this.availability = availability;
        this.status = status;
        this.costumer = costumer;
    }

    @Override
    public Long identity() { return figureId; }

    public String name() { return name; }

    public FigureCategory category() { return category; }

    public FigureAvailability availability() { return availability; }

    public FigureStatus status() { return status; }

    public Costumer costumer() { return costumer; }

    public void UpdateFigureCategory (FigureCategory category) { this.category = category; }

    public void UpdateFigureCostumer (Costumer costumer) { this.costumer = costumer; }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Figure)) return false;
        Figure that = (Figure) other;
        return Objects.equals(figureId, that.figureId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Figure)) return false;
        Figure that = (Figure) o;
        return Objects.equals(figureId, that.figureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(figureId);
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %-3s | Name: %-10s | Status: %-6s | Availability: %-10s | Version: %-6s | Category: %-10s",
                figureId != null ? figureId : "N/A",
                name != null ? name : "N/A",
                status != null ? status : "N/A",
                availability != null ? availability : "N/A",
                version != null ? version.toString() : "N/A",
                category != null ? category.identity() : "N/A"
        );
    }

}
