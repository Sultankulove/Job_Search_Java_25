package kg.attractor.job_search_java_25.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "contact_type")
public class ContactType {

    @Id
    private Long id;

    @Column(name = "type", length = 64, unique = true)
    private String type;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    private List<ContactInfo> contacts;
}
