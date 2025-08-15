package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.dao.mappers.VacancyMapper;
import kg.attractor.job_search_java_25.dto.VacancySearchDto;
import kg.attractor.job_search_java_25.dto.VacancyShortDto;
import kg.attractor.job_search_java_25.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Vacancy> getAllVacanciesById(Long employerId) {
        String sql = "select * from vacancies where author_id=?";
        return jdbcTemplate.query(sql, new VacancyMapper(), employerId);
    }

    public long createVacancy(Vacancy vacancy) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, vacancy.getName());
            ps.setString(2, vacancy.getDescription());
            ps.setLong(3, vacancy.getCategory().getId());
            ps.setDouble(4, vacancy.getSalary());
            ps.setLong(5, vacancy.getExpFrom());
            ps.setLong(6, vacancy.getExpTo());
            ps.setBoolean(7, vacancy.getIsActive());
            ps.setLong(8, vacancy.getAuthor().getId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }


    public void updateTime(Long id) {
        String sql = "update vacancies set update_time=? where id=?";
        jdbcTemplate.update(sql, new Timestamp(System.currentTimeMillis()), id);
    }

    public void editVacancy(Vacancy vacancy, Long vacancyId, Long userId) {
        String sql = "update vacancies set " +
                "name = ?, " +
                "description = ?, " +
                "category_id = ?, " +
                "salary = ?, " +
                "exp_from = ?, " +
                "exp_to = ?, " +
                "is_active = ?, " +
                "update_time = ? " +
                "where id = ? AND author_id = ?";

        jdbcTemplate.update(sql,
                vacancy.getName(),
                vacancy.getDescription(),
                vacancy.getCategory().getId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.getIsActive(),
                new Timestamp(System.currentTimeMillis()),
                vacancyId,
                userId
        );
    }

    public void vacancyIsActive(Long vacancyId, Boolean isActive) {
        String sql = "update vacancies set is_active=? where id=?";
        jdbcTemplate.update(sql, isActive, vacancyId);
    }

    public Optional<Vacancy> getVacancyById(Long id) {
        String sql = "select * from vacancies where id=? limit 1";
        try {
            Vacancy vacancy = jdbcTemplate.queryForObject(sql, new VacancyMapper(), id);
            return Optional.ofNullable(vacancy);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteVacancyById(Long id) {
        String sql = "delete from vacancies where id=?";
        jdbcTemplate.update(sql, id);
    }

    public Long getOwnerId(Long vacancyId) {
        String sql = "select author_id from vacancies where id = ?";
        return jdbcTemplate.query(sql, (rs, i) -> rs.getLong(1), vacancyId)
                .stream().findFirst()
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public List<VacancyShortDto> getActiveShortVacancies() {
        String sql = """
        select name, update_time
        from vacancies
        where is_active = true
        order by update_time desc
    """;

        return jdbcTemplate.query(sql, (rs, i) ->
                new VacancyShortDto(
                        rs.getString("name"),
                        rs.getTimestamp("update_time").toLocalDateTime()
                )
        );
    }

    public List<Vacancy> getShortVacanciesByAuthorId(Long authorId) {
        String sql = "SELECT id, name, update_time FROM vacancies WHERE author_id = ?";
        return jdbcTemplate.query(sql, (rs, i) -> {
            Vacancy v = new Vacancy();
            v.setId(rs.getLong("id"));
            v.setName(rs.getString("name"));
            v.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return v;
        }, authorId);
    }


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
