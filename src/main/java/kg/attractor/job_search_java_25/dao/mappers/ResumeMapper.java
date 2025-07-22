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
        resume.setApplicantId(rs.getLong("applicant_id"));
        resume.setName(rs.getString("name"));
        resume.setCategoryId(rs.getLong("category_id"));
        resume.setSalary(rs.getDouble("salary"));
        resume.setIsActive(rs.getBoolean("is_active"));
        resume.setCreatedDate(rs.getTimestamp("created_date"));
        resume.setUpdateTime(rs.getTimestamp("update_time"));
        return resume;
    }
}
