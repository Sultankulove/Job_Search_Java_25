package kg.attractor.job_search_java_25.repository;

import kg.attractor.job_search_java_25.dto.resumeDtos.nested.EducationInfoDto;
import kg.attractor.job_search_java_25.model.EducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationInfoRepository extends JpaRepository<EducationInfo, Long> {

    EducationInfo getEducationInfoById(Long id);

    EducationInfo getEducationInfoByResume_Id(Long resumeId);

    List<EducationInfo> getEducationInfosByResume_Id(Long resumeId);
}
