package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.dao.mappers.ResumeMapper;
import kg.attractor.job_search_java_25.dto.ResumeSearchDto;
import kg.attractor.job_search_java_25.model.Resume;
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
import java.util.ArrayList;
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
                    ps.setLong(1, resume.getApplicant().getId());
                    ps.setString(2, resume.getName());
                    ps.setLong(3, resume.getCategory().getId());
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
                resume.getCategory().getId(),
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

    public Long getOwnerId(Long resumeId) {
        String sql = "select applicant_id from resumes where id = ?";
        return jdbcTemplate.query(sql, (rs, i) -> rs.getLong(1), resumeId)
                .stream().findFirst()
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public List<Resume> getShortResumesByApplicantId(Long applicantId) {
        String sql = "SELECT id, name, update_time FROM resumes WHERE applicant_id = ?";
        return jdbcTemplate.query(sql, (rs, i) -> {
            Resume r = new Resume();
            r.setId(rs.getLong("id"));
            r.setName(rs.getString("name"));
            r.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return r;
        }, applicantId);
    }

    public List<Resume> searchResumes(ResumeSearchDto criteria) {
        StringBuilder sql = new StringBuilder("SELECT * FROM resumes WHERE is_active = TRUE");
        List<Object> args = new ArrayList<>();

        if (criteria.getCategoryId() != null) {
            sql.append(" AND category_id = ?");
            args.add(criteria.getCategoryId());
        }

        if (criteria.getMinSalary() != null) {
            sql.append(" AND salary >= ?");
            args.add(criteria.getMinSalary());
        }

        if (criteria.getMaxSalary() != null) {
            sql.append(" AND salary <= ?");
            args.add(criteria.getMaxSalary());
        }

        if (criteria.getSearchTerm() != null && !criteria.getSearchTerm().isBlank()) {
            sql.append(" AND LOWER(name) LIKE ?");
            args.add("%" + criteria.getSearchTerm().toLowerCase() + "%");
        }

        String sort = criteria.getSort();
        if (sort == null || sort.isBlank()) {
            sort = "date_desc";
        }
        switch (sort) {
            case "salary_asc" -> sql.append(" ORDER BY salary ASC");
            case "salary_desc" -> sql.append(" ORDER BY salary DESC");
            case "date_asc" -> sql.append(" ORDER BY update_time ASC");
            default -> sql.append(" ORDER BY update_time DESC");
        }

        int page = criteria.getPage() != null && criteria.getPage() > 0 ? criteria.getPage() : 1;
        int size = criteria.getSize() != null && criteria.getSize() > 0 ? criteria.getSize() : 20;
        int offset = (page - 1) * size;

        sql.append(" LIMIT ? OFFSET ?");
        args.add(size);
        args.add(offset);

        return jdbcTemplate.query(sql.toString(), new ResumeMapper(), args.toArray());
    }
}
