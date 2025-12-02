package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.Name;
import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import utils.StringListConverter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static more.ColorfulOutput.*;

@XmlRootElement
@Entity
public class ProposalTemplate implements AggregateRoot<Long>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Name name;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> text;

    @Column
    private Description description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ProposalTemplate() {
        this.createdAt = LocalDateTime.now();
    }

    public ProposalTemplate(Name name, Description description, List<String> text) {
        this();
        this.name = name;
        this.description = description;
        this.text = text;
    }

    @Override
    public Long identity() {
        return id;
    }


    public boolean hasIdentity(String id) {
        try {
            return this.id != null && this.id.equals(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Name name() {
        return name;
    }

    public void changeName(Name name) {
        this.name = name;
    }

    public List<String> text() {
        return text;
    }

    public void changeContent(List<String> text) {
        this.text = text;
    }

    public Description description() {
        return description;
    }

    public void changeDescription(Description description) {
        this.description = description;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof ProposalTemplate)) return false;
        ProposalTemplate that = (ProposalTemplate) other;
        return Objects.equals(this.id, that.id)
                && Objects.equals(this.name, that.name)
                && Objects.equals(this.description, that.description)
                && Objects.equals(this.text, that.text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProposalTemplate)) return false;
        ProposalTemplate that = (ProposalTemplate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    public int compareTo(String other) {
        return this.name.toString().compareToIgnoreCase(other);
    }

    @Override
    public String toString() {
        String desc = description.toString();
        if ("Not provided!".equals(desc)) {
            desc = ANSI_BRIGHT_BLACK + desc + ANSI_RESET;
        }
        return String.format("[%s -> %s]", name, desc);
    }
}