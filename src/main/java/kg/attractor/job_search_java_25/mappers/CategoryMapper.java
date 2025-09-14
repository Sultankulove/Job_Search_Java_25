package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.CategoryDtos.CategoryDto;
import kg.attractor.job_search_java_25.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category c) {
        if (c == null) return null;
        var dto = new CategoryDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        return dto;
    }

    public List<CategoryDto> toDtoList(List<Category> categories) {
        return categories.stream()
                .map(this::toDto)
                .toList();
    }
}
