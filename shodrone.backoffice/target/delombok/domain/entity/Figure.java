package domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "FIGURE")
public class Figure {
    @Id
    private Long id;
}
