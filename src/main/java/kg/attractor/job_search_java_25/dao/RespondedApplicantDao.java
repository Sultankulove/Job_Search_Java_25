package kg.attractor.job_search_java_25.dao;

import kg.attractor.job_search_java_25.model.RespondedApplicant;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RespondedApplicantDao {
    private final JdbcTemplate jdbcTemplate;

    public List<RespondedApplicant> getRespondedApplicantsByResumesId(List<Long> resumeIds) {
        if (resumeIds.isEmpty()) return List.of();

        String inSql = String.join(",", Collections.nCopies(resumeIds.size(), "?"));

        String sql = "select * from responded_applicants where resume_id = in (" + inSql + ")";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, resumeIds.toArray());
        List<RespondedApplicant> respondedApplicants = new ArrayList<>();
        for (Map<String, Object> map : list) {
            RespondedApplicant respondedApplicant = new RespondedApplicant();
            respondedApplicant.setId(((Number) map.get("id")).longValue());
            respondedApplicant.setResumeId(((Number) map.get("resume_id")).longValue());
            respondedApplicant.setVacancyId(((Number) map.get("vacancy_id")).longValue());
            respondedApplicant.setConfirmation((Boolean) map.get("confirmation"));
            respondedApplicants.add(respondedApplicant);
        }
        return respondedApplicants;
    }

    public List<RespondedApplicant> getRespondedApplicantsByVacancyIds(List<Long> vacancyIds) {
        if (vacancyIds.isEmpty()) return List.of();

        String inSql = String.join(",", Collections.nCopies(vacancyIds.size(), "?"));

        String sql = "select * from responded_applicants where vacancy_id in (" + inSql + ")";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, vacancyIds.toArray());


        List<RespondedApplicant> respondedApplicants = new ArrayList<>();
        for (Map<String, Object> map : list) {
            RespondedApplicant respondedApplicant = new RespondedApplicant();
            respondedApplicant.setId(((Number) map.get("id")).longValue());
            respondedApplicant.setResumeId(((Number) map.get("resume_id")).longValue());
            respondedApplicant.setVacancyId(((Number) map.get("vacancy_id")).longValue());
            respondedApplicant.setConfirmation((Boolean) map.get("confirmation"));
            respondedApplicants.add(respondedApplicant);

        }
        return respondedApplicants;
    }

    public void save(RespondedApplicant respondedApplicant) {
        String sql = "insert into responded_applicants (resume_id, vacancy_id, confirmation) values (?, ?, ?)";
        jdbcTemplate.update(sql,
                respondedApplicant.getResumeId(),
                respondedApplicant.getVacancyId(),
                respondedApplicant.getConfirmation()
        );
    }

    public List<RespondedApplicant> getResponsesByVacancy(Long vacancyId) {
        String sql = "select * from responded_applicants where vacancy_id = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, vacancyId);
        List<RespondedApplicant> respondedApplicants = new ArrayList<>();
        for (Map<String, Object> map : list) {
            RespondedApplicant respondedApplicant = new RespondedApplicant();
            respondedApplicant.setId(((Number) map.get("id")).longValue());
            respondedApplicant.setResumeId(((Number) map.get("resume_id")).longValue());
            respondedApplicant.setConfirmation((Boolean) map.get("confirmation"));
            respondedApplicants.add(respondedApplicant);
        }
        return respondedApplicants;
    }
}
