package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.dao.mappers.ResumeMapper;
import kg.attractor.job_search_java_25.dao.mappers.VacancyMapper;
import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public List<Vacancy> findAllActiveVacancies() {
        String sql = "SELECT * FROM vacancies WHERE is_active = true";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public List<Vacancy> findAllVacancies() {
        String sql = "SELECT * FROM vacancies";
        return namedParameterJdbcTemplate.query(sql, new VacancyMapper());
    }
    public List<Vacancy> findByCategoryId(int categoryId) {
        String sql = "SELECT * FROM vacancies WHERE category_id = :categoryId";
        return namedParameterJdbcTemplate.query(
                sql,
                Map.of("category_Id", categoryId),
                new BeanPropertyRowMapper<>(Vacancy.class)
        );
    }

    public List<Vacancy> findByAuthorId(int authorId) {
        String sql = "SELECT * FROM vacancies WHERE author_id = :authorId";
        return namedParameterJdbcTemplate.query(
                sql,
                Map.of("authorId", authorId),
                new BeanPropertyRowMapper<>(Vacancy.class)
        );
    }

    public void save(Vacancy vacancy) {
        String sql = """
            INSERT INTO vacancies (
                name, description, category_id, salary,
                exp_from, exp_to, is_active, author_id,
                created_date, update_time
            ) VALUES (
                :name, :description, :categoryId, :salary,
                :expFrom, :expTo, :isActive, :authorId,
                :createdDate, :updateTime
            )
        """;
        Map<String, Object> params = new HashMap<>();
        params.put("name", vacancy.getName());
        params.put("description", vacancy.getDescription());
        params.put("categoryId", vacancy.getCategoryId());
        params.put("salary", vacancy.getSalary());
        params.put("expFrom", vacancy.getExpFrom());
        params.put("expTo", vacancy.getExpTo());
        params.put("isActive", vacancy.getIsActive());
        params.put("authorId", vacancy.getAuthorId());
        params.put("createdDate", vacancy.getCreatedDate());
        params.put("updateTime", vacancy.getUpdateTime());

        namedParameterJdbcTemplate.update(sql, params);
    }

    public void edit(Long id, Vacancy vacancy) {
        String sql = """
            UPDATE vacancies SET
                name = :name,
                description = :description,
                category_id = :categoryId,
                salary = :salary,
                exp_from = :expFrom,
                exp_to = :expTo,
                is_active = :isActive,
                update_time = :updateTime
            WHERE id = :id
        """;
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", vacancy.getName());
        params.put("description", vacancy.getDescription());
        params.put("categoryId", vacancy.getCategoryId());
        params.put("salary", vacancy.getSalary());
        params.put("expFrom", vacancy.getExpFrom());
        params.put("expTo", vacancy.getExpTo());
        params.put("isActive", vacancy.getIsActive());
        params.put("updateTime", vacancy.getUpdateTime());

        namedParameterJdbcTemplate.update(sql, params);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM vacancies WHERE id = :id";
        namedParameterJdbcTemplate.update(sql, Map.of("id", id));
    }

    public Optional<Vacancy> getVacancyById(Long vacancyId) {
        String sql = "SELECT * FROM VACANCIES WHERE id = ?";
        List<Vacancy> results = jdbcTemplate.query(sql, new VacancyMapper(), vacancyId);
        return Optional.ofNullable(DataAccessUtils.singleResult(results));
    }


}
