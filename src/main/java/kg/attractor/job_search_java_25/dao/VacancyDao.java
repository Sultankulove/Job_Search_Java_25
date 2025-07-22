package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.dao.mappers.VacancyMapper;
import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
        Map<String, Object> params = Map.of(
                "name", vacancy.getName(),
                "description", vacancy.getDescription(),
                "category_Id", vacancy.getCategoryId(),
                "salary", vacancy.getSalary(),
                "expFrom", vacancy.getExpFrom(),
                "expTo", vacancy.getExpTo(),
                "isActive", vacancy.getIsActive(),
                "authorId", vacancy.getAuthorId(),
                "createdDate", vacancy.getCreatedDate(),
                "updateTime", vacancy.getUpdateTime()
        );
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void update(Vacancy vacancy) {
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
        Map<String, Object> params = Map.of(
                "id", vacancy.getId(),
                "name", vacancy.getName(),
                "description", vacancy.getDescription(),
                "categoryId", vacancy.getCategoryId(),
                "salary", vacancy.getSalary(),
                "expFrom", vacancy.getExpFrom(),
                "expTo", vacancy.getExpTo(),
                "isActive", vacancy.getIsActive(),
                "updateTime", vacancy.getUpdateTime()
        );
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void delete(int id) {
        String sql = "DELETE FROM vacancies WHERE id = :id";
        namedParameterJdbcTemplate.update(sql, Map.of("id", id));
    }


}
