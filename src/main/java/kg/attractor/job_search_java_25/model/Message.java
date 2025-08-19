package kg.attractor.job_search_java_25.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {

    @Id
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "responded_applicant_id")
    private RespondedApplicant respondedApplicant;

    @Column(name = "content", length = 2048)
    private String content;

    @Column(name = "timestamp")
    private Timestamp timestamp;
}
