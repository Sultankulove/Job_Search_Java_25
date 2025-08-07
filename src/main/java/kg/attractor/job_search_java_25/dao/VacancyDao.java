package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Vacancy> getVacancies(Long employerId) {
        String sql = "select * from vacancies where author_id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), employerId);
    }
}
