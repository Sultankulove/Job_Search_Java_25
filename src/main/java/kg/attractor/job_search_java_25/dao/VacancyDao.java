package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.dao.mappers.VacancyMapper;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;

//    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Vacancy> getAllVacanciesById(Long employerId) {
        String sql = "select * from vacancies where author_id=?";
        return jdbcTemplate.query(sql, new VacancyMapper(), employerId);
    }

    public List<Vacancy> getShortVacanciesById(Long employerId) {
        String sql = "select name, update_time from vacancies where author_id=?";
        return jdbcTemplate.query(sql, new VacancyMapper(), employerId);
    }

//    public List<Vacancy> getAllVacancies(Long authorId) {
//        String sql ="select * from vacancies where author_id:id";
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("author_id", authorId);
//        return namedParameterJdbcTemplate.query(sql, new VacancyMapper());
//    }

    public void createVacancy(Vacancy vacancy) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time) values (?,?,?,?,?,?,?,?,?,?);",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    ps.setString(1, vacancy.getName());
                    ps.setString(2, vacancy.getDescription());
                    ps.setLong(3, vacancy.getCategoryId());
                    ps.setDouble(4, vacancy.getSalary());
                    ps.setLong(5, vacancy.getExpFrom());
                    ps.setLong(6, vacancy.getExpTo());
                    ps.setBoolean(7, vacancy.getIsActive());
                    ps.setLong(8, vacancy.getAuthorId());
                    ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
                    return ps;
                }, keyHolder
        );

        Number generatedKey = keyHolder.getKey();
        System.out.println("Generated key: " + generatedKey);
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
                vacancy.getCategoryId(),
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

}
