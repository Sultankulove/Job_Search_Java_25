package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.VacancyDao;
import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;

    @Override
    public void editVacancyById(long id, VacancyDto vacancyDto) {
        // TODO редактируем вакансию. Находим по id и перезаписываем его.
    }

    @Override
    public void deleteById(long id) {
        // TODO удаляем вакансию по id.

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
        // TODO Возвращаем список откика на вакансии

        return null;
    }

    @Override
    public void createVacancy(VacancyDto vacancyDto) {

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
                        .id(e.getId())
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
