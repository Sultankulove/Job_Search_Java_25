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
@Table(name = "contacts_info")
public class ContactInfo {

    @Id
    private Long id;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "resume_id")
    private Long resumeId;

    @Column(name = "contact_value")
    private String contactValue;
}
