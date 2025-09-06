package kg.attractor.job_search_java_25.repository;

import kg.attractor.job_search_java_25.model.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {


}
