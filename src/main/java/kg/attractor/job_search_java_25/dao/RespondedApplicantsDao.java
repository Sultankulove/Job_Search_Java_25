package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.model.RespondedApplicant;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RespondedApplicantsDao {
    private final NamedParameterJdbcTemplate jdbc;
    private final JdbcTemplate jdbcTemplate;

    public List<RespondedApplicant> findByResumeId(int resumeId) {
        String sql = "SELECT * FROM responded_applicants WHERE resume_id = :resumeId";
        return jdbc.query(sql, Map.of("resumeId", resumeId), new BeanPropertyRowMapper<>(RespondedApplicant.class));
    }

    public void respondedToVacancy(Long resumeId, Long vacancyId) {
        String sql = """
        INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
        VALUES (?, ?, FALSE)
    """;

        jdbcTemplate.update(sql, resumeId, vacancyId);
    }

    public void save(RespondedApplicant ra) {
        String sql = "INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation) VALUES (:resumeId, :vacancyId, :confirmation)";
        Map<String, Object> params = Map.of(
                "resumeId", ra.getResumeId(),
                "vacancyId", ra.getVacancyId(),
                "confirmation", ra.getConfirmation()
        );
        jdbc.update(sql, params);
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM responded_applicants WHERE id = :id", Map.of("id", id));
    }
}