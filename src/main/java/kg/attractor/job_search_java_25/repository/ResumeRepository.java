package kg.attractor.job_search_java_25.repository;


import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Page<Resume> findByCategory_Id(Long categoryId, Pageable pageable);

    Page<Resume> findAll(Pageable pageable);

    List<Resume> findAllByApplicant_Id(Long applicantId);

    @Transactional
    @Modifying
    @Query("""
           update Resume r
              set r.isActive = :isActive
            where r.id = :resumeId
           """)
    void resumeIsActive(@Param("resumeId") Long resumeId,
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

    List<Resume> getAllByApplicant_Id(Long id);

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

    Page<Resume> findAllByApplicant_Id(Long authorId, Pageable pageable);

    Page<Resume> findAllByApplicant_IdAndCategory_Id(Long authorId, Long categoryId, Pageable pageable);

    Vacancy getResumeById(Long id);
}