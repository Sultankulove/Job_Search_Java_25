package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.CategoryDto;
import kg.attractor.job_search_java_25.model.Category;
import kg.attractor.job_search_java_25.repository.CategoryRepository;
import kg.attractor.job_search_java_25.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
//            categoryDto.setParentId(category.getParent().getId());
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }
}
