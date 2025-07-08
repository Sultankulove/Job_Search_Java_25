package kg.attractor.job_search_java_25.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private Long id;
    private String name;
    private Long parentId;
}
