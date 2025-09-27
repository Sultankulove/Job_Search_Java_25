package kg.attractor.job_search_java_25.repository;

import kg.attractor.job_search_java_25.model.PublicationComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationCommentRepository extends JpaRepository<PublicationComment, Long> {

    List<PublicationComment> findByPublicationIdOrderByCreatedAtAsc(Long publicationId);
}
