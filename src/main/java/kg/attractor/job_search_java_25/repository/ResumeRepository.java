package kg.attractor.job_search_java_25.repository;


import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.model.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Page<Resume> findAllByApplicant_IdAndCategory_Id(Long applicantId, Long categoryId, Pageable pageable);

    @Query("""
              select new kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto(
                r.id, r.name, c.name, r.salary, r.isActive, r.updateTime
              )
              from Resume r
              left join r.category c
              where (:applicantId is null or r.applicant.id = :applicantId)
              order by r.updateTime desc
            """)
    Page<ResumeListItemDto> findList(@Param("applicantId") Long applicantId, Pageable pageable);


    Page<Resume> findByCategory_Id(Long categoryId, Pageable pageable);

    List<Resume> findAllByApplicant_Id(Long applicantId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Resume r set r.isActive = :isActive where r.id = :resumeId")
    int setActive(@Param("resumeId") Long resumeId, @Param("isActive") Boolean isActive);

    @Query("select r.applicant.id from Resume r where r.id = :resumeId")
    Long getOwnerId(@Param("resumeId") Long resumeId);

    Optional<Resume> findFirstByApplicantIdAndIsActiveTrueOrderByUpdateTimeDesc(Long applicantId);

    List<Resume> findAllByApplicantId(Long applicantId);

    @Query("select r.applicant.id from Resume r where r.id = :resumeId")
    Optional<Long> findOwnerIdByResumeId(@Param("resumeId") Long resumeId);

//    List<Resume> getResumeById(Long id);

    Resume getResumeById(Long id);
}