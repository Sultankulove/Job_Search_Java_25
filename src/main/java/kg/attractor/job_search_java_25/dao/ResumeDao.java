package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.dao.mappers.ResumeMapper;
import kg.attractor.job_search_java_25.dao.mappers.VacancyMapper;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Resume> getAllResumesById(Long applicantId) {
        String sql = "select * from resumes where applicant_id=?";
        return jdbcTemplate.query(sql, new ResumeMapper(), applicantId);
    }

    public void createResume(Resume resume) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) values (?,?,?,?,?,?,?);",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    ps.setLong(1, resume.getApplicantId());
                    ps.setString(2, resume.getName());
                    ps.setLong(3, resume.getCategoryId());
                    ps.setDouble(4, resume.getSalary());
                    ps.setBoolean(5, resume.getIsActive());
                    ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                    return ps;
                }, keyHolder
        );
    }

    public void updateTime(Long resumeId) {
        String sql = "update resumes set update_time=? where id=?";
        jdbcTemplate.update(sql, new Timestamp(System.currentTimeMillis()), resumeId);
    }

    public void editResume(Resume resume, Long resumeId, Long applicantId) {
        String sql = "update resumes set " +
                "name = ?, " +
                "category_id = ?, " +
                "salary = ?, " +
                "is_active = ?, " +
                "update_time = ? " +
                "where id = ? AND applicant_id = ?";

        jdbcTemplate.update(sql,
                resume.getName(),
                resume.getCategoryId(),
                resume.getSalary(),
                resume.getIsActive(),
                new Timestamp(System.currentTimeMillis()),
                resumeId, applicantId
        );
    }

    public void resumeIsActive(Long resumeId, Boolean isActive) {
        String sql = "update resumes set is_active=? where id=?";
        jdbcTemplate.update(sql, isActive, resumeId);
    }


    public Optional<Resume> getResumeById(Long id) {
        String sql = "select * from resumes where id=? limit 1";
        try {
            Resume resume = jdbcTemplate.queryForObject(sql, new ResumeMapper(), id);
            return Optional.ofNullable(resume);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteResumeById(Long id) {
        String sql = "delete from resumes where id=?";
        jdbcTemplate.update(sql, id);
    }

}
