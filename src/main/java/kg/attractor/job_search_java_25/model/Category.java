package kg.attractor.job_search_java_25.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "Categories")
public class Category {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Long parentId;
}
