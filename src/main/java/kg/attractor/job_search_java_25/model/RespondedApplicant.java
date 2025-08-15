package kg.attractor.job_search_java_25.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "responded_applicants")
public class RespondedApplicant {

    @Id
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @Column(name = "confirmation")
    private Boolean confirmation;

    @OneToMany(mappedBy = "respondedApplicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;
}
