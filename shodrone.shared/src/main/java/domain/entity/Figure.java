package domain.entity;

import domain.valueObjects.*;
import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import lombok.Setter;
import utils.DslMetadata;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "figure")
public class Figure implements AggregateRoot<Long>, Serializable {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long figureId;

    @Embedded
    @Column(name = "Name", nullable = false)
    private Name name;

    @Embedded
    private Description description;

    @Version
    @Column(name = "entity_version")
    private Long entityVersion;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private FigureCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FigureAvailability availability;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FigureStatus status;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "figure_dsl_versions", joinColumns = @JoinColumn(name = "figure_id"))
    @MapKeyColumn(name = "dsl_version")
    private Map<String, DslMetadata> dslVersions = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Costumer customer;

    protected Figure() {}

    public Figure(Name name, Description description,
                  FigureCategory category, FigureAvailability availability,
                  FigureStatus status, Map<String, DslMetadata> dslVersions,
                  Costumer customer) {
        this.name = Objects.requireNonNull(name);
        this.description = description;
        this.category = category;
        this.availability = Objects.requireNonNull(availability);
        this.status = Objects.requireNonNull(status);
        this.dslVersions = new HashMap<>(dslVersions);
        this.customer = customer;
    }

    @Override
    public Long identity() {
        return figureId;
    }

    public Name name() {
        return name;
    }

    public Description description() {
        return description;
    }

    public FigureCategory category() {
        return category;
    }

    public FigureAvailability availability() {
        return availability;
    }

    public FigureStatus status() {
        return status;
    }

    public Map<String, DslMetadata> dslVersions() {
        return Collections.unmodifiableMap(dslVersions);
    }

    public void addDslVersion(String version, String droneModel, List<String> dslContent) {
        dslVersions.put(version, new DslMetadata(droneModel, dslContent));
    }

    public Costumer customer() {
        return customer;
    }

    public void updateFigureCategory(FigureCategory category) {
        this.category = Objects.requireNonNull(category);
    }

    public void updateCustomer(Costumer customer) {
        this.customer = Objects.requireNonNull(customer);
    }

    public void decommission() {
        if (this.status != FigureStatus.ACTIVE) {
            throw new IllegalStateException("Figure must be active to decommission");
        }
        this.status = FigureStatus.INACTIVE;
    }

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
        Figure figure = (Figure) o;
        return Objects.equals(figureId, figure.figureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(figureId);
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %-3s | Name: %-20s | Description: %-30s | Category: %-20s | Status: %-6s | Availability: %-10s | Customer: %-20s | DSL Versions: %s",
                figureId != null ? figureId : "N/A",
                name,
                description,
                category.identity(),
                status,
                availability,
                customer != null ? customer.name() : "N/A",
                dslVersions.keySet()
        );
    }
}