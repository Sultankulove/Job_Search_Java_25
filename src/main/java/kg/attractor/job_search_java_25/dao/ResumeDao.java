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


    public List<Resume> searchResumes(ResumeSearchDto criteria) {
        StringBuilder sql = new StringBuilder("SELECT * FROM list WHERE is_active = TRUE");
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
