package kg.attractor.job_search_java_25.service.impl;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.attractor.job_search_java_25.dao.VacancyDao;
import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;

    @Override
    public ResponseEntity<VacancyDto> getVacancyById(Long id) {
        Optional<Vacancy> optionalVacancy = vacancyDao.getVacancyById(id);

        if (optionalVacancy.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Vacancy vacancy = optionalVacancy.get();
        VacancyDto dto = new VacancyDto();
        dto.setName(vacancy.getName());
        dto.setDescription(vacancy.getDescription());
        dto.setCategoryId(vacancy.getCategoryId());
        dto.setSalary(vacancy.getSalary());
        dto.setExpFrom(vacancy.getExpFrom());
        dto.setExpTo(vacancy.getExpTo());
        dto.setIsActive(vacancy.getIsActive());
        dto.setAuthorId(vacancy.getAuthorId());

        dto.setCreatedDate(vacancy.getCreatedDate().toLocalDateTime());
        dto.setUpdateTime(vacancy.getUpdateTime().toLocalDateTime());

        return ResponseEntity.ok(dto);
    }

    @Override
    public Vacancy editVacancyById(Long id, VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(id);
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setAuthorId(vacancyDto.getAuthorId());
        vacancy.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));

        vacancyDao.edit(id, vacancy);
        return vacancy;
        // TODO редактируем вакансию. Находим по id и перезаписываем его.
    }

    @Override
    public void deleteById(Long id) {
        vacancyDao.delete(id);
    }

    @Override
    public ResponseEntity<List<VacancyDto>> getActiveVacancies() {

        // TODO Возвращаем список всех активных вакансий
        return null;
    }

    @Override
    public ResponseEntity<List<VacancyDto>> getVacancyByCategoryName(String name) {
        return null;
    }


    @Override
    public ResponseEntity<List<ApplicantDto>> getApplicantResponded() {
        // TODO Возвращаем список моих откликов на вакансии

        return null;
    }

    @Override
    public Vacancy createVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(true);
        vacancy.setAuthorId(vacancyDto.getAuthorId());
        vacancy.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        vacancy.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));

        vacancyDao.save(vacancy);
        return vacancy;
    }

    @Override
    public ResponseEntity<List<VacancyDto>> getVacancyByCategoryId(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<VacancyDto>> getVacancySortBySalary() {
        return null;
    }

    @Override
    public List<VacancyDto> getAllVacancies() {
        List<Vacancy> list = vacancyDao.findAllVacancies();
        return list.stream()
                .map(e -> VacancyDto.builder()
                        .name(e.getName())
                        .description(e.getDescription())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .expFrom(e.getExpFrom())
                        .expTo(e.getExpTo())
                        .isActive(e.getIsActive())
                        .authorId(e.getAuthorId())
                        .createdDate(e.getCreatedDate().toLocalDateTime())
                        .updateTime(e.getUpdateTime().toLocalDateTime())
                        .build())
                .toList();
    }
}
