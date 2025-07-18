package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.model.WorkExperienceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WorkExperienceDao {
    private final NamedParameterJdbcTemplate jdbc;

    public List<WorkExperienceInfo> findByResumeId(int resumeId) {
        String sql = "SELECT * FROM work_experience_info WHERE resume_id = :resumeId";
        return jdbc.query(sql, Map.of("resumeId", resumeId), new BeanPropertyRowMapper<>(WorkExperienceInfo.class));
    }

    public void save(WorkExperienceInfo work) {
        String sql = """
            INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
            VALUES (:resumeId, :years, :company, :position, :responsibilities)
        """;
        Map<String, Object> params = Map.of(
                "resumeId", work.getResumeId(),
                "years", work.getYears(),
                "company", work.getCompanyName(),
                "position", work.getPosition(),
                "responsibilities", work.getResponsibilities()
        );
        jdbc.update(sql, params);
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM work_experience_info WHERE id = :id", Map.of("id", id));
    }
}