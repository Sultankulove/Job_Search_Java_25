package kg.attractor.job_search_java_25.repository;


import jakarta.validation.constraints.*;
import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.dto.ResumeListViewDto;
import kg.attractor.job_search_java_25.dto.ResumeShortDto;
import kg.attractor.job_search_java_25.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
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


    @Query("""
        select ResumeListViewDto(
            a.id,            
            c.id,           
            r.name,          
            r.salary,        
            r.isActive,      
            r.createdDate,   
            r.updateTime  
        )
        from Resume r
          join r.applicant a
          join r.category c
        where (:applicantId is null or a.id = :applicantId)
        order by r.updateTime desc
        """)
    List<ResumeListViewDto> findAllForList(@Param("applicantId") Long applicantId);

    @Query("""
        select new kg.attractor.job_search_java_25.dto.ResumeDto(
        r.applicant.id,
        r.name,
        r.category.id,
        r.salary,
        r.isActive,
        r.updateTime,
        r.createdDate) from Resume r"""
    )
    List<ResumeDto> findAllResumes();
}