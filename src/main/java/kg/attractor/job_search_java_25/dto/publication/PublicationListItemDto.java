package kg.attractor.job_search_java_25.dto.publication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationListItemDto {
    private Long id;
    private String title;
    private String categoryName;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String cover;
    private long commentCount;
}
