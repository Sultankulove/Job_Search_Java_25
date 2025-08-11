package kg.attractor.job_search_java_25.dto;

import lombok.Data;

@Data
public class VacancySearchDto {
Long categoryId;

    private Float minSalary;

    private Float maxSalary;

    private String searchTerm;

    private String sort;

    private Integer page;

    private Integer size;
}