package kg.attractor.job_search_java_25.repository;


import kg.attractor.job_search_java_25.dto.ResumeShortDto;
import kg.attractor.job_search_java_25.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findAllByApplicant_Id(Long applicantId);

    @Transactional
    @Modifying
    @Query("""
           update Resume r
              set r.isActive = :isActive
            where r.id = :resumeId
           """)
    int resumeIsActive(@Param("resumeId") Long resumeId,
                       @Param("isActive") Boolean isActive);



    @Query("select r.applicant.id from Resume r where r.id = :resumeId")
    Long getOwnerId(@Param("resumeId") Long resumeId);


    @Query("""
    select new kg.attractor.job_search_java_25.dto.ResumeShortDto(
       r.name,
       r.updateTime
    )
    from Resume r
    where r.applicant.id = :applicantId
    order by r.updateTime desc
    """)
    List<ResumeShortDto> getShortResumesByApplicantId(@Param("applicantId") Long applicantId);


}
