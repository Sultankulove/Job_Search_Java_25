package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.RespondedApplicantDao;
import kg.attractor.job_search_java_25.dao.VacancyDao;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final RespondedApplicantDao respondedApplicantDao;

    @Override
    public List<VacancyShortDto> getShortVacanciesList(Long employerId) {
        log.debug("VacancyService.getShortVacanciesList(employerId={})", employerId);
        List<VacancyShortDto> shortVacancies;
        shortVacancies = vacancyDao.getAllVacanciesById(employerId)
                .stream()
                .map(v -> new VacancyShortDto(v.getName(), v.getUpdateTime()))
                .toList();
        log.info("Вакансии: короткий список size={}", shortVacancies.size());
        return shortVacancies;
    }

    @Override
    public ResponseEntity<?> updateTime(Long id) {
        log.info("Вакансии: обновление времени id={}", id);
        vacancyDao.updateTime(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void editVacancy(VacancyEditDto editVacancyEditDto, Long vacancyId, Long userId) {
        log.info("Вакансии: редактирование id={}, authorId={}", vacancyId, userId);
        Vacancy vacancy = new Vacancy();
        vacancy.setName(editVacancyEditDto.getName());
        vacancy.setDescription(editVacancyEditDto.getDescription());
        vacancy.setCategoryId(editVacancyEditDto.getCategoryId());
        vacancy.setSalary(editVacancyEditDto.getSalary());
        vacancy.setExpFrom(editVacancyEditDto.getExpFrom());
        vacancy.setExpTo(editVacancyEditDto.getExpTo());
        vacancy.setIsActive(editVacancyEditDto.getIsActive());

        vacancyDao.editVacancy(vacancy, vacancyId, userId);
        log.debug("Вакансии: отредактировано id={}", vacancyId);
    }

    @Override
    public void vacancyIsActiveById(Long vacancyId, VacancyIsActiveDto vacancyIsActiveDto) {
        boolean isActive = vacancyIsActiveDto.getIsActive();
        log.info("Вакансии: публикация id={}, isActive={}", vacancyId, isActive);
        vacancyDao.vacancyIsActive(vacancyId, isActive);
    }

    @Override
    public VacancyDto createVacancies(Long authorId, VacancyEditDto createVacancyEditDto) {
        log.info("Вакансии: создание authorId={}", authorId);
        Vacancy v = new Vacancy();
        v.setName(createVacancyEditDto.getName());
        v.setDescription(createVacancyEditDto.getDescription());
        v.setCategoryId(createVacancyEditDto.getCategoryId());
        v.setSalary(createVacancyEditDto.getSalary());
        v.setExpFrom(createVacancyEditDto.getExpFrom());
        v.setExpTo(createVacancyEditDto.getExpTo());
        v.setIsActive(createVacancyEditDto.getIsActive());
        v.setAuthorId(authorId);

        long id = vacancyDao.createVacancy(v);
        v.setId(id);

        log.debug("Вакансии: создано (name={}, categoryId={})", v.getName(), v.getCategoryId());
        return VacancyDto.builder()
                .id(v.getId())
                .name(v.getName())
                .description(v.getDescription())
                .categoryId(v.getCategoryId())
                .salary(v.getSalary())
                .expFrom(v.getExpFrom())
                .expTo(v.getExpTo())
                .isActive(v.getIsActive())
                .authorId(v.getAuthorId())
                .createdDate(v.getCreatedDate())
                .updateDate(v.getUpdateTime())
                .build();
    }

    @Override
    public ResponseEntity<?> getVacancyById(Long id) {
        Optional<Vacancy> vacancy = vacancyDao.getVacancyById(id);
        if (vacancy.isPresent()) {
            log.info("Вакансии: найдено id={}", id);
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
            log.warn("Вакансии: не найдено id={}", id);
            throw new NotFoundException("Vacancy with id=" + id + " not found");
        }
    }

    @Override
    public void deleteVacancyById(Long id) {
        log.warn("Вакансии: удаление id={}", id);
        vacancyDao.deleteVacancyById(id);
    }

    @Override
    public void respondToVacancy(ResponseDto dto, Long userId) {
        log.info("Отклик: vacancyId={}, resumeId={}, userId={}", dto.getVacancyId(), dto.getResumeId(), userId);
        RespondedApplicant respondedApplicant = new RespondedApplicant();
        respondedApplicant.setResumeId(dto.getResumeId());
        respondedApplicant.setVacancyId(dto.getVacancyId());
        respondedApplicant.setConfirmation(false);
        respondedApplicantDao.save(respondedApplicant);
        log.debug("Отклик: сохранён");
    }

    @Override
    public ResponseEntity<List<RespondedApplicantDto>> getResponsesByVacancy(Long vacancyId) {
        log.debug("Отклики: запрос списка по vacancyId={}", vacancyId);
        List<RespondedApplicant> responded = respondedApplicantDao.getResponsesByVacancy(vacancyId);
        List<RespondedApplicantDto> dtos = responded.stream()
                .map(entity -> RespondedApplicantDto.builder()
                        .id(entity.getId())
                        .resumeId(entity.getResumeId())
                        .vacancyId(entity.getVacancyId())
                        .confirmation(entity.getConfirmation())
                        .build())
                .toList();
        log.info("Отклики: найдено {}", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public List<VacancyShortDto> getPublicShortVacancies() {
        var list = vacancyDao.getActiveShortVacancies();
        log.info("Публичные вакансии: {}", list.size());
        return list;
    }

    @Override
    public void editVacancyOwned(VacancyEditDto dto, Long vacancyId, Long employerId) {
        log.debug("editVacancyOwned(vacancyId={}, employerId={})", vacancyId, employerId);
        requireVacancyOwner(vacancyId, employerId);
        Vacancy v = new Vacancy();
        v.setName(dto.getName());
        v.setDescription(dto.getDescription());
        v.setCategoryId(dto.getCategoryId());
        v.setSalary(dto.getSalary());
        v.setExpFrom(dto.getExpFrom());
        v.setExpTo(dto.getExpTo());
        v.setIsActive(dto.getIsActive());
        vacancyDao.editVacancy(v, vacancyId, employerId);
        log.info("Вакансия {} отредактирована работодателем {}", vacancyId, employerId);
    }

    @Override
    public void vacancyIsActiveOwned(Long vacancyId, VacancyIsActiveDto dto, Long employerId) {
        log.debug("vacancyIsActiveOwned(vacancyId={}, employerId={})", vacancyId, employerId);
        requireVacancyOwner(vacancyId, employerId);
        vacancyDao.vacancyIsActive(vacancyId, dto.getIsActive());
        log.info("Статус активности вакансии {} изменён работодателем {} на {}", vacancyId, employerId, dto.getIsActive());
    }

    private void requireVacancyOwner(Long vacancyId, Long employerId) {
        Long ownerId = vacancyDao.getOwnerId(vacancyId);
        if (!ownerId.equals(employerId)) throw new ForbiddenException("Not your vacancy");
    }

    @Override
    public List<VacancyDto> searchVacancies(VacancySearchDto criteria) {
        log.debug("VacancyService.searchVacancies(criteria={})", criteria);
        List<Vacancy> found = vacancyDao.searchVacancies(criteria);
        return found.stream().map(v -> {
            VacancyDto dto = new VacancyDto();
            dto.setId(v.getId());
            dto.setName(v.getName());
            dto.setDescription(v.getDescription());
            dto.setCategoryId(v.getCategoryId());
            dto.setSalary(v.getSalary());
            dto.setExpFrom(v.getExpFrom());
            dto.setExpTo(v.getExpTo());
            dto.setIsActive(v.getIsActive());
            dto.setAuthorId(v.getAuthorId());
            dto.setCreatedDate(v.getCreatedDate());
            dto.setUpdateDate(v.getUpdateTime());
            return dto;
        }).toList();
    }
}
