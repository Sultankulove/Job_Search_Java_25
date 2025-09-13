package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.CategoryDtos.CategoryDto;
import kg.attractor.job_search_java_25.model.Category;

import java.util.List;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        if (category == null) return null;

        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public static List<CategoryDto> toDtoList(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::toDto)
                .toList();
    }
}
