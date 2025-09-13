package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.CategoryDtos.CategoryDto;
import kg.attractor.job_search_java_25.mappers.CategoryMapper;
import kg.attractor.job_search_java_25.repository.CategoryRepository;
import kg.attractor.job_search_java_25.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDto).toList();
    }
}
