package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.model.EducationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EducationInfoDao {
    private final NamedParameterJdbcTemplate jdbc;

    public List<EducationInfo> findByResumeId(int resumeId) {
        String sql = "SELECT * FROM education_info WHERE resume_id = :resumeId";
        return jdbc.query(sql, Map.of("resumeId", resumeId), new BeanPropertyRowMapper<>(EducationInfo.class));
    }

    public void save(EducationInfo edu) {
        String sql = """
            INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
            VALUES (:resumeId, :institution, :program, :startDate, :endDate, :degree)
        """;
        Map<String, Object> params = Map.of(
                "resumeId", edu.getResumeId(),
                "institution", edu.getInstitution(),
                "program", edu.getProgram(),
                "startDate", edu.getStartDate(),
                "endDate", edu.getEndDate(),
                "degree", edu.getDegree()
        );
        jdbc.update(sql, params);
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM education_info WHERE id = :id", Map.of("id", id));
    }
}