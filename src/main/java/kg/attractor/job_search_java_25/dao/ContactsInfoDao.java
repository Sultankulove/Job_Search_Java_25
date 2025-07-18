package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.model.ContactInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ContactsInfoDao {
    private final NamedParameterJdbcTemplate jdbc;

    public List<ContactInfo> findByResumeId(int resumeId) {
        String sql = "SELECT * FROM contacts_info WHERE resume_id = :resumeId";
        return jdbc.query(sql, Map.of("resumeId", resumeId), new BeanPropertyRowMapper<>(ContactInfo.class));
    }

    public void save(ContactInfo contact) {
        String sql = """
            INSERT INTO contacts_info (type_id, resume_id, contact_value)
            VALUES (:typeId, :resumeId, :value)
        """;
        Map<String, Object> params = Map.of(
                "typeId", contact.getTypeId(),
                "resumeId", contact.getResumeId(),
                "contact_value", contact.getValue()
        );
        jdbc.update(sql, params);
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM contacts_info WHERE id = :id", Map.of("id", id));
    }
}