package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.CategoryDtos.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
}
