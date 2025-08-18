package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.dao.mappers.VacancyMapper;
import kg.attractor.job_search_java_25.dto.VacancySearchDto;
import kg.attractor.job_search_java_25.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Vacancy> searchVacancies(VacancySearchDto criteria) {
        StringBuilder sql = new StringBuilder("SELECT * FROM vacancies WHERE is_active = TRUE");
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

        return jdbcTemplate.query(sql.toString(), new VacancyMapper(), args.toArray());
    }
}
