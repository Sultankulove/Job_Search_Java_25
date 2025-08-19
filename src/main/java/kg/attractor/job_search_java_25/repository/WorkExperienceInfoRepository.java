package kg.attractor.job_search_java_25.repository;

import kg.attractor.job_search_java_25.model.WorkExperienceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceInfoRepository extends JpaRepository<WorkExperienceInfo, Integer> {
    void deleteWorkExperienceInfoById(Long id);
}
