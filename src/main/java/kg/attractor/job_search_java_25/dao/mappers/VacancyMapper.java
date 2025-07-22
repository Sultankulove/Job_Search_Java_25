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
        vacancy.setCategoryId(rs.getLong("category_id"));
        vacancy.setSalary(rs.getDouble("salary"));
        vacancy.setExpFrom(rs.getLong("exp_from"));
        vacancy.setExpTo(rs.getLong("exp_to"));
        vacancy.setIsActive(rs.getBoolean("is_active"));
        vacancy.setAuthorId(rs.getLong("author_id"));
        vacancy.setCreatedDate(rs.getTimestamp("created_date"));
        vacancy.setUpdatedDate(rs.getTimestamp("update_date"));
        return vacancy;
    }
}
