package kg.attractor.job_search_java_25.repository;

import kg.attractor.job_search_java_25.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByRespondedApplicant_IdOrderByTimestampAsc(Long responseId);

    List<Message> getMessagesByRespondedApplicant_IdOrderByTimestampAsc(Long respondedApplicantId);

}
