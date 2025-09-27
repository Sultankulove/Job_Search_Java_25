package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.publication.comment.PublicationCommentDto;
import kg.attractor.job_search_java_25.model.Publication;
import kg.attractor.job_search_java_25.model.PublicationComment;
import kg.attractor.job_search_java_25.model.User;
import org.springframework.stereotype.Component;

@Component
public class PublicationCommentMapper {

    public PublicationCommentDto toDto(PublicationComment entity, boolean canDelete) {
        if (entity == null) return null;
        User author = entity.getAuthor();
        return PublicationCommentDto.builder()
                .id(entity.getId())
                .authorId(author != null ? author.getId() : null)
                .authorName(author != null ? author.getName() : null)
                .authorAvatar(author != null ? author.getAvatar() : null)
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .canDelete(canDelete)
                .deleted(entity.isDeleted())
                .deletedByModerator(entity.isDeletedByModerator())
                .build();
    }

    public PublicationComment toEntity(Publication publication, User author, String content) {
        PublicationComment entity = new PublicationComment();
        entity.setPublication(publication);
        entity.setAuthor(author);
        entity.setContent(content);
        return entity;
    }
}
