package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.RespondedApplicantDao;
import kg.attractor.job_search_java_25.dao.VacancyDao;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final RespondedApplicantDao respondedApplicantDao;

    @Override
    public List<VacancyShortDto>getShortVacanciesList(Long employerId) {

        List<VacancyShortDto> shortVacancies;
        shortVacancies = vacancyDao.getAllVacanciesById(employerId)
                .stream()
                .map(v -> new VacancyShortDto(v.getName(), v.getUpdateTime()))
                .toList();
        return shortVacancies;
    }

    @Override
    public ResponseEntity<Void> updateTime(Long id) {
        vacancyDao.updateTime(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void editVacancy(VacancyEditDto editVacancyEditDto, Long vacancyId, Long userId) {

        Vacancy vacancy = new Vacancy();
        vacancy.setName(editVacancyEditDto.getName());
        vacancy.setDescription(editVacancyEditDto.getDescription());
        vacancy.setCategoryId(editVacancyEditDto.getCategoryId());
        vacancy.setSalary(editVacancyEditDto.getSalary());
        vacancy.setExpFrom(editVacancyEditDto.getExpFrom());
        vacancy.setExpTo(editVacancyEditDto.getExpTo());
        vacancy.setIsActive(editVacancyEditDto.getIsActive());


        vacancyDao.editVacancy(vacancy, vacancyId, userId);
    }

    @Override
    public void vacancyIsActiveById(Long vacancyId, VacancyIsActiveDto vacancyIsActiveDto) {

        boolean isActive = vacancyIsActiveDto.getIsActive();

        vacancyDao.vacancyIsActive(vacancyId, isActive);
    }

    @Override
    public VacancyEditDto createVacancies(Long authorId, VacancyEditDto createVacancyEditDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setName(createVacancyEditDto.getName());
        vacancy.setDescription(createVacancyEditDto.getDescription());
        vacancy.setCategoryId(createVacancyEditDto.getCategoryId());
        vacancy.setSalary(createVacancyEditDto.getSalary());
        vacancy.setExpFrom(createVacancyEditDto.getExpFrom());
        vacancy.setExpTo(createVacancyEditDto.getExpTo());
        vacancy.setIsActive(createVacancyEditDto.getIsActive());
        vacancy.setAuthorId(authorId);


        vacancyDao.createVacancy(vacancy);
        return VacancyEditDto.builder()
                        .name(vacancy.getName())
                                .description(vacancy.getDescription())
                                        .categoryId(vacancy.getId())
                                                .salary(vacancy.getSalary())
                                                        .expFrom(vacancy.getExpFrom())
                                                                .expTo(vacancy.getExpTo())
                                                                        .isActive(vacancy.getIsActive()).
                build();
    }

    @Override
    public ResponseEntity<VacancyDto> getVacancyById(Long id) {
        Optional<Vacancy> vacancy = vacancyDao.getVacancyById(id);
        if (vacancy.isPresent()) {
            VacancyDto dto = new VacancyDto();
            dto.setId(vacancy.get().getId());
            dto.setName(vacancy.get().getName());
            dto.setDescription(vacancy.get().getDescription());
            dto.setCategoryId(vacancy.get().getCategoryId());
            dto.setSalary(vacancy.get().getSalary());
            dto.setExpFrom(vacancy.get().getExpFrom());
            dto.setExpTo(vacancy.get().getExpTo());
            dto.setIsActive(vacancy.get().getIsActive());
            dto.setAuthorId(vacancy.get().getAuthorId());
            dto.setCreatedDate(vacancy.get().getCreatedDate());
            dto.setUpdateDate(vacancy.get().getUpdateTime());

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public void deleteVacancyById(Long id) {
        vacancyDao.deleteVacancyById(id);
    }

    @Override
    public void respondToVacancy(ResponseDto dto, Long userId) {
        RespondedApplicant respondedApplicant = new RespondedApplicant();
        respondedApplicant.setResumeId(dto.getResumeId());
        respondedApplicant.setVacancyId(dto.getVacancyId());
        respondedApplicant.setConfirmation(false);
        respondedApplicantDao.save(respondedApplicant);
    }

    @Override
    public ResponseEntity<List<RespondedApplicantDto>> getResponsesByVacancy(Long vacancyId) {
        List<RespondedApplicant> responded = respondedApplicantDao.getResponsesByVacancy(vacancyId);

        List<RespondedApplicantDto> dtos = responded.stream()
                .map(entity -> RespondedApplicantDto.builder()
                        .id(entity.getId())
                        .resumeId(entity.getResumeId())
                        .vacancyId(entity.getVacancyId())
                        .confirmation(entity.getConfirmation())
                        .build())
                .toList();

        return ResponseEntity.ok(dtos);
    }



}
