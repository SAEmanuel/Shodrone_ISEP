package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.Name;
import jakarta.persistence.*;
import more.ColorfulOutput;
import utils.StringListConverter;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static more.ColorfulOutput.*;

@XmlRootElement
@Entity
public class ProposalTemplate {

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
        this.text = text;
        this.description = description;
    }


    public Long identity() {
        return id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProposalTemplate that = (ProposalTemplate) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        String desc = description.toString();
        if ("Not provided!".equals(desc)) {
            desc = ANSI_BRIGHT_BLACK + desc + ANSI_RESET;
        }
        return String.format("[%s -> %s] ", name, desc);
    }

}
