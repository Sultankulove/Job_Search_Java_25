package kg.attractor.job_search_java_25.dao.mappers;

import kg.attractor.job_search_java_25.model.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong("id"));
        category.setName(rs.getString("name"));
        category.setParentId(rs.getLong("parent_id"));
        return category;
    }
}
