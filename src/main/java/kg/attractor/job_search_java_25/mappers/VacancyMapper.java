package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.ActiveDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyUpsertDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyViewDto;
import kg.attractor.job_search_java_25.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VacancyMapper {

    public VacancyViewDto toView(Vacancy v) {
        if (v == null) return null;
        return new VacancyViewDto(
                v.getId(),
                v.getAuthor() != null ? v.getAuthor().getId() : null,
                v.getCategory()  != null ? v.getCategory().getId()  : null,
                v.getCategory()  != null ? v.getCategory().getName(): null,
                v.getName(),
                v.getDescription(),
                v.getSalary(),
                v.getExpFrom(),
                v.getExpTo(),
                v.getIsActive(),
                v.getCreatedDate(),
                v.getUpdateTime()
        );
    }

    public VacancyListItemDto toListItem(Vacancy v) {
        if (v == null) return null;
        return new VacancyListItemDto(
                v.getId(),
                v.getName(),
                v.getCategory() != null ? v.getCategory().getName() : null,
                v.getSalary(),
                v.getIsActive(),
                v.getUpdateTime()
        );
    }

    public Vacancy applyUpsert(VacancyUpsertDto d, Vacancy e, Category category, User author) {
        e.setName(d.getName());
        e.setDescription(d.getDescription());
        e.setCategory(category);
        e.setSalary(d.getSalary());
        e.setExpFrom(d.getExpFrom());
        e.setExpTo(d.getExpTo());
        e.setIsActive(Boolean.TRUE.equals(d.getActive()));
        if (author != null) e.setAuthor(author);
        return e;
    }


    public void applyActive(ActiveDto dto, Vacancy e) {
        if (dto != null && dto.getActive() != null) e.setIsActive(dto.getActive());
    }

    public List<VacancyListItemDto> toListItems(List<Vacancy> list) {
        return list == null ? List.of() : list.stream().map(this::toListItem).toList();
    }
    public List<VacancyViewDto> toViews(List<Vacancy> list) {
        return list == null ? List.of() : list.stream().map(this::toView).toList();
    }
}
