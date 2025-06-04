package domain.entity;

import domain.valueObjects.Content;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import jakarta.persistence.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement
@Entity
public class ProposalTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Name name;

    @Column(nullable = false, columnDefinition = "Content")
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
    public String toString() {
        return "ShowTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
