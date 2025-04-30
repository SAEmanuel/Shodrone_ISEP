package domain.entity;

import authz.Email;
import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.Description;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.strings.util.StringPredicates;
import jakarta.persistence.*;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;

@XmlRootElement
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = { "categoryName"})})
public class FigureCategory implements AggregateRoot<String>, Serializable {

    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 80;
    private static final int DESCRIPTION_MIN_LENGTH = 5;
    private static final int DESCRIPTION_MAX_LENGTH = 300;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Version
    private Long version;

    @XmlElement
    @JsonProperty
    @Column(nullable = false, unique = true)
    private Name categoryName;

    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    private Description description;

    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    private boolean active;

    @XmlElement
    @JsonProperty
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @XmlElement
    @JsonProperty
    @Column(nullable = false, updatable = false)
    private Email createdBy;

    @XmlElement
    @JsonProperty
    private LocalDateTime updatedOn;

    @XmlElement
    @JsonProperty
    private Email updatedBy;


    protected FigureCategory() {
        // For ORM
    }


    private static boolean nameMeetsMinimumRequirements(final Name name) {
        if (name == null || !StringPredicates.isNullOrEmpty(name.toString())) {
            return false;
        }
        String trimmed = name.toString().trim();
        int length = trimmed.length();
        return length >= NAME_MIN_LENGTH && length <= NAME_MAX_LENGTH;
    }

    private static boolean descriptionMeetsMinimumRequirements(final Description description) {
        if (description == null || !StringPredicates.isNonEmpty(description.toString())) {
            return false;
        }
        String trimmed = description.toString().trim();
        int length = trimmed.length();
        return length >= DESCRIPTION_MIN_LENGTH && length <= DESCRIPTION_MAX_LENGTH;
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
        this.categoryName = newCategoryName;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean toggleState() {
        this.active = !this.active;
        return isActive();
    }

    public Description description() {
        return this.description;
    }

    @Override
    public String identity() {
        return this.categoryName.toString();
    }

    @Override
    public boolean hasIdentity(final String id) {
        return id.equalsIgnoreCase(this.categoryName.toString());
    }

    @Override
    public boolean sameAs(Object other) {
        final FigureCategory figureCategory = (FigureCategory) other;
        return this.equals(figureCategory) && description().equals(figureCategory.description()) && isActive() == figureCategory.isActive();
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
//        // Usa emojis da biblioteca JEmoji
//        String statusEmoji = active ? Emojis.GREEN_CIRCLE.getEmoji() : Emojis.RED_CIRCLE.getEmoji();
//        StringBuilder sb = new StringBuilder();
//        sb.append(Emojis.LABEL.getEmoji()).append(" ").append(categoryName)
//                .append(" | ").append(Emojis.MEMO.getEmoji()).append(" ").append(description)
//                .append(" | ").append(statusEmoji).append(active ? " Active" : " Inactive")
//                .append(" | ").append(Emojis.ALARM_CLOCK.getEmoji()).append(" Created: ").append(createdOn)
//                .append(" ").append(Emojis.BUST_IN_SILHOUETTE.getEmoji()).append(" ").append(createdBy);
//        if (updatedOn != null) {
//            sb.append(" | ").append(Emojis.ALARM_CLOCK.getEmoji()).append(" Updated: ").append(updatedOn)
//                    .append(" ").append(Emojis.BUST_IN_SILHOUETTE.getEmoji()).append(" ").append(updatedBy);
//        }
//        return sb.toString();
//    }
}
