package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.publication.PublicationDetailDto;
import kg.attractor.job_search_java_25.dto.publication.PublicationListItemDto;
import kg.attractor.job_search_java_25.dto.publication.PublicationUpsertDto;
import kg.attractor.job_search_java_25.dto.publication.comment.PublicationCommentDto;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.mappers.PublicationCommentMapper;
import kg.attractor.job_search_java_25.mappers.PublicationMapper;
import kg.attractor.job_search_java_25.model.Category;
import kg.attractor.job_search_java_25.model.Publication;
import kg.attractor.job_search_java_25.model.PublicationComment;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.repository.CategoryRepository;
import kg.attractor.job_search_java_25.repository.PublicationCommentRepository;
import kg.attractor.job_search_java_25.repository.PublicationRepository;
import kg.attractor.job_search_java_25.repository.UserRepository;
import kg.attractor.job_search_java_25.repository.spec.PublicationSpecifications;
import kg.attractor.job_search_java_25.service.PublicationService;
import kg.attractor.job_search_java_25.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;
    private final PublicationCommentRepository commentRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PublicationMapper publicationMapper;
    private final PublicationCommentMapper commentMapper;

    private static final String COVER_DIR = "publications";

    @Override
    @Transactional(readOnly = true)
    public Page<PublicationListItemDto> findPublications(Pageable pageable,
                                                         Long categoryId,
                                                         String sort,
                                                         String term,
                                                         boolean includeDeleted) {
        Sort sortOrder = resolveSort(sort);
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);

        String normalizedTerm = (term == null || term.isBlank()) ? null : term.trim();
        Specification<Publication> spec = Specification.allOf(
                includeDeleted ? null : PublicationSpecifications.notDeleted(),
                PublicationSpecifications.categoryEquals(categoryId),
                PublicationSpecifications.matchesTerm(normalizedTerm)
        );


        return publicationRepository.findAll(spec, pageRequest)
                .map(pub -> publicationMapper.toListItem(pub, publicationMapper.countVisibleComments(pub)));
    }

    @Override
    @Transactional(readOnly = true)
    public PublicationDetailDto getPublication(Long id, Long viewerId, boolean viewerIsModerator) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Publication id=" + id));

        if (publication.isDeleted() && !isOwnerOrModerator(publication, viewerId, viewerIsModerator)) {
            throw new NotFoundException("Данная публикация была удалена.");
        }

        List<PublicationComment> commentEntities = commentRepository.findByPublicationIdOrderByCreatedAtAsc(publication.getId());
        List<PublicationCommentDto> comments = new ArrayList<>(commentEntities.size());
        for (PublicationComment comment : commentEntities) {
            boolean canDelete = viewerIsModerator || Objects.equals(viewerId, comment.getAuthor() != null ? comment.getAuthor().getId() : null);
            PublicationCommentDto dto = commentMapper.toDto(comment, canDelete);
            comments.add(dto);
        }

        return publicationMapper.toDetail(publication, comments);
    }

    @Override
    @Transactional
    public PublicationDetailDto createPublication(PublicationUpsertDto dto, Long authorId) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("User id=" + authorId));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category id=" + dto.getCategoryId()));

        Publication entity = new Publication();
        publicationMapper.applyUpsert(dto, entity, category, author, extractCover(dto));
        Publication saved = publicationRepository.save(entity);
        return getPublication(saved.getId(), authorId, false);
    }

    @Override
    @Transactional
    public PublicationDetailDto updatePublication(Long id, PublicationUpsertDto dto, Long authorId) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Publication id=" + id));
        if (!Objects.equals(publication.getAuthor() != null ? publication.getAuthor().getId() : null, authorId)) {
            throw new ForbiddenException("Not your publication");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category id=" + dto.getCategoryId()));

        publicationMapper.applyUpsert(dto, publication, category, publication.getAuthor(), extractCover(dto));
        Publication saved = publicationRepository.save(publication);
        return getPublication(saved.getId(), authorId, false);
    }

    @Override
    @Transactional
    public void softDeletePublication(Long id, Long requesterId, boolean requesterIsModerator) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Publication id=" + id));
        if (!isOwnerOrModerator(publication, requesterId, requesterIsModerator)) {
            throw new ForbiddenException("Not your publication");
        }
        if (!publication.isDeleted()) {
            publication.setDeletedAt(LocalDateTime.now());
            publicationRepository.save(publication);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicationListItemDto> findRecentByAuthor(Long authorId, int limit) {
        if (limit <= 0) {
            return List.of();
        }
        var items = publicationRepository.findByAuthorIdAndDeletedAtIsNullOrderByUpdatedAtDesc(
                authorId,
                PageRequest.of(0, limit)
        );
        List<PublicationListItemDto> dtos = new ArrayList<>(items.size());
        for (Publication publication : items) {
            dtos.add(publicationMapper.toListItem(publication, publicationMapper.countVisibleComments(publication)));
        }
        return dtos;
    }

    @Override
    @Transactional
    public PublicationCommentDto addComment(Long publicationId, Long authorId, String content) {
        Publication publication = publicationRepository.findByIdAndDeletedAtIsNull(publicationId)
                .orElseThrow(() -> new NotFoundException("Publication id=" + publicationId));
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("User id=" + authorId));

        PublicationComment entity = commentMapper.toEntity(publication, author, content);
        PublicationComment saved = commentRepository.save(entity);
        return commentMapper.toDto(saved, true);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long requesterId, boolean requesterIsModerator) {
        PublicationComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment id=" + commentId));

        Long authorId = comment.getAuthor() != null ? comment.getAuthor().getId() : null;
        if (!requesterIsModerator && !Objects.equals(authorId, requesterId)) {
            throw new ForbiddenException("Not your comment");
        }
        if (!comment.isDeleted()) {
            comment.setDeletedAt(LocalDateTime.now());
            if (requesterIsModerator && !Objects.equals(authorId, requesterId)) {
                comment.setDeletedByModerator(true);
            }
            commentRepository.save(comment);
        }
    }

    @Override
    public String storeCover(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return FileUtil.saveUploadedFile(file, COVER_DIR);
    }

    private String extractCover(PublicationUpsertDto dto) {
        MultipartFile coverFile = dto.getCover();
        if (coverFile != null && !coverFile.isEmpty()) {
            return storeCover(coverFile);
        }
        return dto.isRemoveCover() ? "" : null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PublicationListItemDto> findMyPublications(Long authorId, Pageable pageable, String sort, String term) {
        Sort sortOrder = resolveSort(sort);
        Pageable pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);

        String normalizedTerm = (term == null || term.isBlank()) ? null : term.trim();

        Specification<Publication> spec = Specification.allOf(
                PublicationSpecifications.notDeleted(),
                PublicationSpecifications.authorEquals(authorId),
                PublicationSpecifications.matchesTerm(normalizedTerm)
        );

        return publicationRepository.findAll(spec, pr)
                .map(pub -> publicationMapper.toListItem(pub, publicationMapper.countVisibleComments(pub)));

    }

    private boolean isOwnerOrModerator(Publication publication, Long userId, boolean isModerator) {
        Long ownerId = publication.getAuthor() != null ? publication.getAuthor().getId() : null;
        return isModerator || Objects.equals(ownerId, userId);
    }

    private Sort resolveSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "updatedAt");
        }
        boolean desc = sort.startsWith("-");
        String field = desc ? sort.substring(1) : sort;
        Sort.Direction direction = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        return switch (field) {
            case "createdAt", "updatedAt", "title" -> Sort.by(direction, field);
            default -> Sort.by(Sort.Direction.DESC, "updatedAt");
        };
    }
}
