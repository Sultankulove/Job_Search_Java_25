package kg.attractor.job_search_java_25.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", length = 64)
    private String name;

    @Column(name="description", length = 512)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "exp_from")
    private Integer expFrom;

    @Column(name = "exp_to")
    private Integer expTo;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespondedApplicant> responses;
}
