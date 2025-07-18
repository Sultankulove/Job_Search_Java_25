package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Resume> findByApplicantId(int applicantId) {
        String sql = "SELECT * FROM resumes WHERE applicant_id = :applicantId";
        return namedParameterJdbcTemplate.query(
                sql,
                Map.of("applicant_Id", applicantId),
                new BeanPropertyRowMapper<>(Resume.class)
        );
    }

    public List<Resume> findAll(){
        String sql = "SELECT * FROM resumes";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }

    public List<Resume> findByCategoryId(int categoryId) {
        String sql = "SELECT * FROM resumes WHERE category_id = :categoryId";
        return namedParameterJdbcTemplate.query(
                sql,
                Map.of("categoryId", categoryId),
                new BeanPropertyRowMapper<>(Resume.class)
        );
    }

    public void save(Resume resume) {
        String sql = """
            INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time)
            VALUES (:applicantId, :name, :categoryId, :salary, :isActive, :createdDate, :updateTime)
        """;
        Map<String, Object> params = Map.of(
                "applicant_Id", resume.getApplicantId(),
                "name", resume.getName(),
                "category_Id", resume.getCategoryId(),
                "salary", resume.getSalary(),
                "is_Active", resume.getIsActive(),
                "created_Date", resume.getCreatedDate(),
                "update_Time", resume.getUpdatedDate()
        );
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void update(Resume resume) {
        String sql = """
            UPDATE resumes SET
                name = :name,
                category_id = :categoryId,
                salary = :salary,
                is_active = :isActive,
                update_time = :updateTime
            WHERE id = :id
        """;
        Map<String, Object> params = Map.of(
                "id", resume.getId(),
                "name", resume.getName(),
                "category_Id", resume.getCategoryId(),
                "salary", resume.getSalary(),
                "is_Active", resume.getIsActive(),
                "update_Time", resume.getUpdatedDate()
        );
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM resumes WHERE id = :id";
        namedParameterJdbcTemplate.update(sql, Map.of("id", id));
    }

}
