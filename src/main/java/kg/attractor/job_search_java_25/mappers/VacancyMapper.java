package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.dto.VacancyEditDto;
import kg.attractor.job_search_java_25.model.Vacancy;

import java.util.List;

public class VacancyMapper {

    public static VacancyDto toDto(Vacancy vacancy) {
        if (vacancy == null) return null;

        VacancyDto dto = new VacancyDto();
        dto.setId(vacancy.getId());
        dto.setName(vacancy.getName());
        dto.setDescription(vacancy.getDescription());
        dto.setCategoryId(vacancy.getCategory().getId());
        dto.setSalary(vacancy.getSalary());
        dto.setExpFrom(vacancy.getExpFrom());
        dto.setExpTo(vacancy.getExpTo());
        dto.setIsActive(vacancy.getIsActive());
        dto.setAuthorId(vacancy.getAuthor().getId());
        dto.setCreatedDate(vacancy.getCreatedDate());
        dto.setUpdateTime(vacancy.getUpdateTime());
        return dto;
    }

    public static List<VacancyDto> toDtoList(List<Vacancy> vacancies) {
        return vacancies.stream()
                .map(VacancyMapper::toDto)
                .toList();
    }

    public static VacancyEditDto toEditDto(Vacancy vacancy) {
        if (vacancy == null) return null;
        VacancyEditDto dto = new VacancyEditDto();
        dto.setId(vacancy.getId());
        dto.setName(vacancy.getName());
        dto.setDescription(vacancy.getDescription());
        dto.setCategoryId(vacancy.getCategory().getId());
        dto.setSalary(vacancy.getSalary());
        dto.setExpFrom(vacancy.getExpFrom());
        dto.setExpTo(vacancy.getExpTo());
        dto.setActive(vacancy.getIsActive());
        return dto;
    }
}
