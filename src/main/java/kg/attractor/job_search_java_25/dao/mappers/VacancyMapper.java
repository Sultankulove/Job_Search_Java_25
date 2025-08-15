package kg.attractor.job_search_java_25.dao.mappers;

import kg.attractor.job_search_java_25.model.Vacancy;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacancyMapper implements RowMapper<Vacancy> {
    @Override
    public Vacancy mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(rs.getLong("id"));
        vacancy.setName(rs.getString("name"));
        vacancy.setDescription(rs.getString("description"));
        vacancy.getCategory().setId(rs.getLong("category_id"));
        vacancy.setSalary(rs.getFloat("salary"));
        vacancy.setExpFrom(rs.getInt("exp_from"));
        vacancy.setExpTo(rs.getInt("exp_to"));
        vacancy.setIsActive(rs.getBoolean("is_active"));
        vacancy.getAuthor().setId(rs.getLong("author_id"));
        vacancy.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        vacancy.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
        return vacancy;
    }
}
