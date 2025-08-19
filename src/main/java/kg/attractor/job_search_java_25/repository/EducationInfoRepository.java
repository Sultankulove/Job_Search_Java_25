package kg.attractor.job_search_java_25.repository;

import kg.attractor.job_search_java_25.model.EducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationInfoRepository extends JpaRepository<EducationInfo, Integer> {

}
