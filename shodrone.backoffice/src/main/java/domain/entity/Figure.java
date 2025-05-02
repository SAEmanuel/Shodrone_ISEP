package domain.entity;

import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;
import domain.valueObjects.Version;
import eapli.framework.domain.model.AggregateRoot;
import domain.valueObjects.Description;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import static more.ColorfulOutput.*;

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
    
    public Figure(Long figureId, String name, Description description,
                  Version version, FigureCategory category, FigureAvailability availability, FigureStatus status,Costumer costumer) {

        this.figureId = figureId;
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

    public FigureAvailability availability() { return availability; }

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

    @Override
    public String toString() {
        return ANSI_BRIGHT_PURPLE + "Figure{" + ANSI_RESET +
                ANSI_BRIGHT_PURPLE+ "\nFigureId=" + figureId + ANSI_RESET +
                ANSI_BRIGHT_PURPLE + "\nName='" + name + '\'' + ANSI_RESET +
                ANSI_BRIGHT_PURPLE + "\nDescription=" + description + ANSI_RESET +
                ANSI_BRIGHT_PURPLE + "\nVersion=" + version + ANSI_RESET +
                ANSI_BRIGHT_PURPLE + "\nCategory=" + category + ANSI_RESET +
                ANSI_BRIGHT_PURPLE + "\nAvailability=" + availability.toString() + ANSI_RESET +
                ANSI_BRIGHT_PURPLE + "\nStatus=" + status.toString() + ANSI_RESET +
                ANSI_BRIGHT_PURPLE + "\nCostumer=" + costumer.identity() + ANSI_RESET +
                ANSI_BRIGHT_PURPLE + "\n}" + ANSI_RESET ;
    }
}
