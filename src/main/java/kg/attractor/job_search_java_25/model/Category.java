package kg.attractor.job_search_java_25.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    private Long id;

    @Column(name = "name", length = 128, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Vacancy> vacancies;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Resume> resumes;

}
