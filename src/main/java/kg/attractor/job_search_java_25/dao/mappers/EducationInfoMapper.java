package kg.attractor.job_search_java_25.dao.mappers;

import kg.attractor.job_search_java_25.model.EducationInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EducationInfoMapper implements RowMapper<EducationInfo> {
    @Override
    public EducationInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        EducationInfo educationInfo = new EducationInfo();
        educationInfo.setId(rs.getLong("id"));
        educationInfo.setResumeId(rs.getLong("resume_id"));
        educationInfo.setInstitution(rs.getString("institution"));
        educationInfo.setProgram(rs.getString("program"));
        educationInfo.setStartDate(rs.getDate("start_date"));
        educationInfo.setEndDate(rs.getDate("end_date"));
        educationInfo.setDegree(rs.getString("degree"));
        return educationInfo;
    }
}
