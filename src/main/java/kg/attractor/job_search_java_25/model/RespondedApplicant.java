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
@Table(name = "Responded_applicants")
public class RespondedApplicant {

    @Id
    private Long id;

    @Column(name = "resume_id")
    private Long resumeId;

    @Column(name = "vacancy_id")
    private Long vacancyId;

    @Column(name = "confirmation")
    private Boolean confirmation;
}
