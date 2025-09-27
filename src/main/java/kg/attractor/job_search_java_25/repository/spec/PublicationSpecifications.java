package kg.attractor.job_search_java_25.repository.spec;

import kg.attractor.job_search_java_25.model.Publication;
import org.springframework.data.jpa.domain.Specification;

public final class PublicationSpecifications {

    private PublicationSpecifications() {
    }

    public static Specification<Publication> notDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("deletedAt"));
    }

    public static Specification<Publication> categoryEquals(Long categoryId) {
        return (categoryId == null)
                ? null
                : (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Publication> matchesTerm(String term) {
        if (term == null || term.isBlank()) {
            return null;
        }
        String like = "%" + term.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("title")), like),
                cb.like(cb.lower(root.get("content")), like)
        );
    }

    public static Specification<Publication> authorEquals(Long authorId) {
        return (root, query, cb) -> {
            if (authorId == null) {
                return null;
            }
            return cb.equal(root.get("author").get("id"), authorId);
        };
    }

}
