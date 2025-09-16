package kg.attractor.job_search_java_25.repository;

import kg.attractor.job_search_java_25.model.RespondedApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface RespondedApplicantRepository extends JpaRepository<RespondedApplicant, Long> {
    Optional<RespondedApplicant> findByVacancyIdAndResumeId(Long vacancyId, Long resumeId);

    List<RespondedApplicant> findAllByVacancyId(Long vacancyId);

    List<RespondedApplicant> findAllByResumeIdIn(List<Long> resumeIds);

    boolean existsByVacancy_IdAndResume_Id(Long vacancyId, Long resumeId);

    @Query("""
           select r
             from RespondedApplicant r
            where r.resume.id in :resumeIds
           """)
    List<RespondedApplicant> getRespondedApplicantsByResumesId(@Param("resumeIds") List<Long> resumeIds);


    @Query("""
           select r
             from RespondedApplicant r
            where r.vacancy.id in :vacancyIds
           """)
    List<RespondedApplicant> getRespondedApplicantsByVacancyIds(@Param("vacancyIds") List<Long> vacancyIds);

    @Query("select r from RespondedApplicant r where r.vacancy.id = :vacancyId order by r.id desc")
    List<RespondedApplicant> getResponsesByVacancy(@Param("vacancyId") Long vacancyId);
    boolean existsByResume_IdAndVacancy_Id(Long resumeId, Long vacancyId);

    default List<RespondedApplicant> getRespondedApplicantsByResumesIdSafe(List<Long> resumeIds) {
        if (resumeIds == null || resumeIds.isEmpty()) return List.of();
        return getRespondedApplicantsByResumesId(resumeIds);
    }

    default List<RespondedApplicant> getRespondedApplicantsByVacancyIdsSafe(List<Long> vacancyIds) {
        if (vacancyIds == null || vacancyIds.isEmpty()) return List.of();
        return getRespondedApplicantsByVacancyIds(vacancyIds);
    }
}
