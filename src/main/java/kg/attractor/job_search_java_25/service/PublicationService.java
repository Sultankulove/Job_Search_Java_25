package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.publication.PublicationDetailDto;
import kg.attractor.job_search_java_25.dto.publication.PublicationListItemDto;
import kg.attractor.job_search_java_25.dto.publication.PublicationUpsertDto;
import kg.attractor.job_search_java_25.dto.publication.comment.PublicationCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PublicationService {

    Page<PublicationListItemDto> findPublications(Pageable pageable,
                                                  Long categoryId,
                                                  String sort,
                                                  String term,
                                                  boolean includeDeleted);

    PublicationDetailDto getPublication(Long id, Long viewerId, boolean viewerIsModerator);

    PublicationDetailDto createPublication(PublicationUpsertDto dto, Long authorId);

    PublicationDetailDto updatePublication(Long id, PublicationUpsertDto dto, Long authorId);

    void softDeletePublication(Long id, Long requesterId, boolean requesterIsModerator);

    List<PublicationListItemDto> findRecentByAuthor(Long authorId, int limit);

    PublicationCommentDto addComment(Long publicationId, Long authorId, String content);

    void deleteComment(Long commentId, Long requesterId, boolean requesterIsModerator);

    String storeCover(MultipartFile file);
}
