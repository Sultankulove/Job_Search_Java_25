package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.publication.PublicationDetailDto;
import kg.attractor.job_search_java_25.dto.publication.PublicationListItemDto;
import kg.attractor.job_search_java_25.dto.publication.PublicationUpsertDto;
import kg.attractor.job_search_java_25.dto.publication.comment.PublicationCommentDto;
import kg.attractor.job_search_java_25.model.Category;
import kg.attractor.job_search_java_25.model.Publication;
import kg.attractor.job_search_java_25.model.PublicationComment;
import kg.attractor.job_search_java_25.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublicationMapper {

    public PublicationListItemDto toListItem(Publication entity, long commentCount) {
        if (entity == null) return null;
        return PublicationListItemDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : null)
                .authorName(entity.getAuthor() != null ? entity.getAuthor().getName() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .cover(entity.getCover())
                .commentCount(commentCount)
                .build();
    }

    public PublicationDetailDto toDetail(Publication entity, List<PublicationCommentDto> comments) {
        if (entity == null) return null;
        User author = entity.getAuthor();
        Category category = entity.getCategory();
        return PublicationDetailDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .authorId(author != null ? author.getId() : null)
                .authorName(author != null ? author.getName() : null)
                .authorAvatar(author != null ? author.getAvatar() : null)
                .categoryId(category != null ? category.getId() : null)
                .categoryName(category != null ? category.getName() : null)
                .cover(entity.getCover())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deleted(entity.isDeleted())
                .comments(comments)
                .build();
    }

    public Publication applyUpsert(PublicationUpsertDto dto, Publication entity, Category category, User author, String coverPath) {
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        if (category != null) {
            entity.setCategory(category);
        }
        if (author != null && entity.getAuthor() == null) {
            entity.setAuthor(author);
        }
        if (coverPath != null) {
            entity.setCover(coverPath);
        } else if (dto.isRemoveCover()) {
            entity.setCover(null);
        }
        return entity;
    }

    public long countVisibleComments(Publication entity) {
        if (entity == null || entity.getComments() == null) return 0;
        return entity.getComments().stream().filter(c -> !isCommentDeleted(c)).count();
    }

    private boolean isCommentDeleted(PublicationComment comment) {
        return comment == null || comment.getDeletedAt() != null;
    }
}
