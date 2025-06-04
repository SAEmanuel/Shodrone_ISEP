package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.Name;
import jakarta.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement
@Entity
public class ProposalTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Name name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    private Description description;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    public ProposalTemplate() {
        this.createdAt = LocalDateTime.now();
    }

    public ProposalTemplate(Name name, String content, Description description) {
        this();
        this.name = name;
        this.content = content;
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

    public String content() {
        return content;
    }

    public void changeContent(String content) {
        this.content = content;
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
