package kg.attractor.job_search_java_25.dao.mappers;

import kg.attractor.job_search_java_25.model.Resume;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResumeMapper implements RowMapper<Resume> {
    @Override
    public Resume mapRow(ResultSet rs, int rowNum) throws SQLException {
        Resume resume = new Resume();
        resume.setId(rs.getLong("id"));
        resume.getApplicant().setId(rs.getLong("applicant_id"));
        resume.setName(rs.getString("name"));
        resume.getCategory().setId(rs.getLong("category_id"));
        resume.setSalary(rs.getFloat("salary"));
        resume.setActive(rs.getBoolean("is_active"));
        resume.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        resume.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
        return resume;
    }
}
