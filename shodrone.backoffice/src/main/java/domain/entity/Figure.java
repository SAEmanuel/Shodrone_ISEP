package domain.entity;

import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;
import domain.valueObjects.Version;
import eapli.framework.domain.model.AggregateRoot;
import more.Description;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "figure")
public class Figure implements AggregateRoot<Long>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "identification", nullable = false, unique = true)
    private Long figureId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Embedded
    private Description description;

    @Embedded
    private Version version;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private FigureCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FigureAvailability availability;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FigureStatus status;

    @OneToOne
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;

    protected Figure() {}
    
    public Figure(String name, Description description,
                  Version version, FigureCategory category, FigureAvailability availability, FigureStatus status,Costumer costumer) {

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

    public FigureStatus status() { return status; }

    public Costumer costumer() { return costumer; }

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
}
