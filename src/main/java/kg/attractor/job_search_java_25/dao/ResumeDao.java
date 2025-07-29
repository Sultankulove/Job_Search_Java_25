package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.dao.mappers.ResumeMapper;
import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public List<Resume> findByApplicantId(int applicantId) {
        String sql = "SELECT * FROM resumes WHERE applicant_id = :applicantId";
        return namedParameterJdbcTemplate.query(
                sql,
                Map.of("applicantId", applicantId),
                new ResumeMapper()
        );
    }

    public List<Resume> findAll(){
        String sql = "SELECT * FROM resumes";
        return namedParameterJdbcTemplate.query(sql, new ResumeMapper());
    }

    public List<Resume> findByCategoryId(Long categoryId) {
        String sql = "SELECT * FROM resumes WHERE category_id = :categoryId";
        return namedParameterJdbcTemplate.query(
                sql,
                Map.of("categoryId", categoryId),
                new ResumeMapper()
        );
    }

    public List<Resume> findByCategoryName(String name) {
        String sql = """
        SELECT r.* FROM resumes r
        JOIN categories c ON r.category_id = c.id
        WHERE c.name = :name
        """;
        return namedParameterJdbcTemplate.query(
                sql,
                Map.of("name", name),
                new ResumeMapper()
        );
    }

    public Optional<Resume> getResumeById(Long resumeId) {
        String sql = "SELECT * FROM RESUMES WHERE id = ?";
        List<Resume> results = jdbcTemplate.query(sql, new ResumeMapper(), resumeId);
        return Optional.ofNullable(DataAccessUtils.singleResult(results));
    }

    public Optional<Resume> getResumeByIdAndUserId(Long resumeId, Optional<Long> userId) {
        String sql = "SELECT * FROM resumes WHERE id = ? AND RESUMES.APPLICANT_ID = ?";
        List<Resume> results = jdbcTemplate.query(sql, new ResumeMapper(), resumeId, userId);
        return Optional.ofNullable(DataAccessUtils.singleResult(results));
    }

    public void createResume(ResumeDto resumeDto) {
        String sql = """
            INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time)
            VALUES (:applicantId, :name, :categoryId, :salary, :isActive, :createdDate, :updateTime)""";
        namedParameterJdbcTemplate.update(
                sql,
        new MapSqlParameterSource()
                .addValue("applicant_id", resumeDto.getApplicantId())
                .addValue("name", resumeDto.getName())
                .addValue("category_id", resumeDto.getCategoryId())
                .addValue("salary", resumeDto.getSalary())
                .addValue("is_active", resumeDto.getIsActive())
                .addValue("created_date", LocalDateTime.now())
                .addValue("update_date", LocalDateTime.now())
        );
    }

    public void edit(Long id ,ResumeDto resumeDto) {
        String sql = """
            UPDATE resumes SET
                applicant_id = :applicantId,
                name = :name,
                category_id = :categoryId,
                salary = :salary,
                is_active = :isActive,
                update_time = :updateTime
            WHERE id = :id
        """;

        namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("id", id)
                        .addValue("applicantId", resumeDto.getApplicantId())
                        .addValue("name", resumeDto.getName())
                        .addValue("categoryId", resumeDto.getCategoryId())
                        .addValue("salary", resumeDto.getSalary())
                        .addValue("isActive", resumeDto.getIsActive())
                        .addValue("updatedTime", System.currentTimeMillis())
        );
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM resumes WHERE id = :id";
        namedParameterJdbcTemplate.update(sql, Map.of("id", id));
    }

}
