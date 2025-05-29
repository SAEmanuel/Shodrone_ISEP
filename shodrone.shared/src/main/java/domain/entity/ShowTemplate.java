package domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class ShowTemplate {
    @Id
    private Long id;

}
