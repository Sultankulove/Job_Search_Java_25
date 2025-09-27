package kg.attractor.job_search_java_25.repository;

import kg.attractor.job_search_java_25.model.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long>, JpaSpecificationExecutor<Publication> {

    Optional<Publication> findByIdAndDeletedAtIsNull(Long id);

    Page<Publication> findByDeletedAtIsNull(Pageable pageable);

    List<Publication> findTop5ByAuthorIdAndDeletedAtIsNullOrderByUpdatedAtDesc(Long authorId);

    List<Publication> findByAuthorIdAndDeletedAtIsNullOrderByUpdatedAtDesc(Long authorId, PageRequest of);

    Page<Publication> findByAuthorIdAndDeletedAtIsNull(Long authorId, Pageable pageable);

}
