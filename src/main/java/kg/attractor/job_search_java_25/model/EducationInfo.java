package kg.attractor.job_search_java_25.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "education_info")
public class EducationInfo {

    @Id
    private Long id;

    @Column(name = "resume_id")
    private Long resumeId;

    @Column(name = "institution")
    private String institution;

    @Column(name = "program")
    private String program;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "degree")
    private String degree;
}
