package domain.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.strings.util.StringPredicates;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Setter;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import more.ColorfulOutput;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;

@XmlRootElement
@Entity
public class FigureCategory implements AggregateRoot<String>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Version
    private Long version;

    @XmlElement
    @JsonProperty
    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 80)
    private Name name;

    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    @Size(max = 300)
    private Description description;

    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    private boolean available;

    @XmlElement
    @JsonProperty
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @XmlElement
    @JsonProperty
    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "created_by", nullable = false, updatable = false))
    private Email createdBy;

    @XmlElement
    @JsonProperty
    private LocalDateTime updatedOn;

    @Setter
    @XmlElement
    @JsonProperty
    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "updated_by"))
    private Email updatedBy;


    protected FigureCategory() {
        // For ORM
    }

    public FigureCategory(Name name, Description description, Email createdBy) {
        if (!nameMeetsMinimumRequirements(name)) {
            throw new IllegalArgumentException("Invalid Category name.");
        }
        if (!descriptionMeetsMinimumRequirements(description)) {
            throw new IllegalArgumentException("Invalid Category Description.");
        }
        if (createdBy == null) {
            throw new IllegalArgumentException("CreatedBy cannot be null.");
        }

        this.name = name;
        this.description = description;
        this.available = true;
        this.createdOn = LocalDateTime.now();
        this.createdBy = createdBy;
        this.updatedOn = null;
        this.updatedBy = null;
    }

    public FigureCategory(Name name, Email createdBy) {
        this(name, new Description("Description not provided!"), createdBy);
    }


    private static boolean nameMeetsMinimumRequirements(final Name name) {
        return !StringPredicates.isNullOrEmpty(name.toString()) && !StringPredicates.isNullOrWhiteSpace(name.toString());
    }

    private static boolean descriptionMeetsMinimumRequirements(final Description description) {
        return description.toString() != null;
    }


    public void changeDescriptionTo(final Description newDescription) {
        if (!descriptionMeetsMinimumRequirements(newDescription)) {
            throw new IllegalArgumentException();
        }
        this.description = newDescription;
    }

    public void changeCategoryNameTo(final Name newCategoryName) {
        if (!nameMeetsMinimumRequirements(newCategoryName)) {
            throw new IllegalArgumentException();
        }
        this.name = newCategoryName;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void toggleState() {
        this.available = !this.available;
    }

    public Description description() {
        return this.description;
    }

    public void updateTime() {
        this.updatedOn = LocalDateTime.now();
    }

    public Long id() {
        return this.pk;
    }

    public LocalDateTime updatedOn() {
        return this.updatedOn;
    }

    @Override
    public String identity() {
        return this.name.toString();
    }


    public Name name(){
        return this.name;
    }
    public Email createdBy() {
        return this.createdBy;
    }

    public Email updatedBy(){
        return this.updatedBy;
    }

    @Override
    public boolean hasIdentity(final String id) {
        return id.equalsIgnoreCase(this.name.toString());
    }

    @Override
    public boolean sameAs(Object other) {
        final FigureCategory figureCategory = (FigureCategory) other;
        return this.equals(figureCategory) && description().equals(figureCategory.description()) && isAvailable() == figureCategory.isAvailable();
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("FigureCategory {")
//                .append("categoryName='").append(name).append('\'')
//                .append(", description='").append(description).append('\'')
//                .append(", active=").append(active)
//                .append(", createdOn=").append(createdOn)
//                .append(", createdBy=").append(createdBy);
//        if (updatedOn != null) {
//            sb.append(", updatedOn=").append(updatedOn);
//        }
//        if (updatedBy != null) {
//            sb.append(", updatedBy=").append(updatedBy);
//        }
//        sb.append('}');
//        return sb.toString();
//    }

    public FigureCategory copy() {
        FigureCategory copy = new FigureCategory(this.name(), this.description(), this.createdBy);
        copy.available = this.available;
        return copy;
    }

    @Override
    public String toString() {
        if (isAvailable()) {
            return String.format("[%s -> %s] %s(%s)%s", name, description, ColorfulOutput.ANSI_FOREST_GREEN, "ACTIVE", ColorfulOutput.ANSI_RESET);
        }
        return String.format("[%s -> %s] %s(%s)%s" , name, description, ColorfulOutput.ANSI_BRIGHT_BLACK, "INACTIVE", ColorfulOutput.ANSI_RESET);
    }

}
