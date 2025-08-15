package kg.attractor.job_search_java_25.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vacancies")
public class Vacancy {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "exp_from")
    private Integer expFrom;

    @Column(name = "exp_to")
    private Integer expTo;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
