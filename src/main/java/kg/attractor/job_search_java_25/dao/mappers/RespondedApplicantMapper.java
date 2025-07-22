package kg.attractor.job_search_java_25.dao.mappers;

import kg.attractor.job_search_java_25.model.RespondedApplicant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RespondedApplicantMapper implements RowMapper<RespondedApplicant> {
    @Override
    public RespondedApplicant mapRow(ResultSet rs, int rowNum) throws SQLException {
        RespondedApplicant respondedApplicant = new RespondedApplicant();
        respondedApplicant.setId(rs.getLong("id"));
        respondedApplicant.setResumeId(rs.getLong("resume_id"));
        respondedApplicant.setVacancyId(rs.getLong("vacancy_id"));
        respondedApplicant.setConfirmation(rs.getBoolean("confirmation"));
        return respondedApplicant;
    }
}
