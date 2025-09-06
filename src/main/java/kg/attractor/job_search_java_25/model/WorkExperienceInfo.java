package kg.attractor.job_search_java_25.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "work_experience_info")
public class WorkExperienceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(name = "years")
    private Integer years;

    @Column(name = "company_name", length = 128)
    private String companyName;

    @Column(name = "position", length = 128)
    private String position;

    @Column(name = "responsibilities", length = 128)
    private String responsibilities;
}
