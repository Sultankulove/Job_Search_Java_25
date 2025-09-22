package kg.attractor.job_search_java_25.repository;

import kg.attractor.job_search_java_25.dto.resumeDtos.nested.WorkExperienceInfoDto;
import kg.attractor.job_search_java_25.model.WorkExperienceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperienceInfoRepository extends JpaRepository<WorkExperienceInfo, Long> {
    void deleteWorkExperienceInfoById(Long id);

    WorkExperienceInfoDto getWorkExperienceInfoById(Long id);

    List<WorkExperienceInfo> getWorkExperienceInfosByResume_Id(Long resumeId);
}
