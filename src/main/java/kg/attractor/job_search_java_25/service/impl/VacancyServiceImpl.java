package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.VacancyDao;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.repository.RespondedApplicantRepository;
import kg.attractor.job_search_java_25.repository.VacancyRepository;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final VacancyRepository vacancyRepository;
    private final RespondedApplicantRepository respondedApplicantRepository;

    @Override
    public List<VacancyShortDto> getShortVacanciesList(Long employerId, Pageable p) {
        log.debug("VacancyService.getShortVacanciesList(employerId={})", employerId);
        List<VacancyShortDto> shortVacancies;

        shortVacancies = vacancyRepository.findAllByAuthor_Id(employerId, p)
                        .stream()
                                .map(vacancy -> new VacancyShortDto(vacancy.getName(), vacancy.getUpdateTime()))
                                        .toList();
        log.info("Вакансии: короткий список size={}", shortVacancies.size());
        return shortVacancies;
    }

    @Override
    public ResponseEntity<?> updateTime(Long id) {
        log.info("Вакансии: обновление времени id={}", id);
        vacancyRepository.updateTime(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void editVacancy(VacancyEditDto editVacancyEditDto, Long vacancyId, Long userId) {
        log.info("Вакансии: редактирование id={}, authorId={}", vacancyId, userId);
        Vacancy vacancy = new Vacancy();
        vacancy.setName(editVacancyEditDto.getName());
        vacancy.setDescription(editVacancyEditDto.getDescription());
        vacancy.getCategory().setId(editVacancyEditDto.getCategoryId());
        vacancy.setSalary(editVacancyEditDto.getSalary());
        vacancy.setExpFrom(editVacancyEditDto.getExpFrom());
        vacancy.setExpTo(editVacancyEditDto.getExpTo());
        vacancy.setIsActive(editVacancyEditDto.getIsActive());

        vacancyRepository.saveVacancy_IdUser_Id(vacancy, vacancyId, userId);
        log.debug("Вакансии: отредактировано id={}", vacancyId);
    }

    @Override
    public void vacancyIsActiveById(Long vacancyId, VacancyIsActiveDto vacancyIsActiveDto) {
        boolean isActive = vacancyIsActiveDto.getIsActive();
        log.info("Вакансии: публикация id={}, isActive={}", vacancyId, isActive);
        vacancyRepository.vacancyIsActive(vacancyId, isActive);
    }

    @Override
    public VacancyDto createVacancies(Long authorId, VacancyEditDto createVacancyEditDto) {
        log.info("Вакансии: создание authorId={}", authorId);
        Vacancy v = new Vacancy();
        v.setName(createVacancyEditDto.getName());
        v.setDescription(createVacancyEditDto.getDescription());
        v.getCategory().setId(createVacancyEditDto.getCategoryId());
        v.setSalary(createVacancyEditDto.getSalary());
        v.setExpFrom(createVacancyEditDto.getExpFrom());
        v.setExpTo(createVacancyEditDto.getExpTo());
        v.setIsActive(createVacancyEditDto.getIsActive());
        v.getAuthor().setId(authorId);

        Vacancy vacancy = vacancyRepository.save(v);

        v.setId(vacancy.getId());

        log.debug("Вакансии: создано (name={}, categoryId={})", v.getName(), v.getCategory().getId());
        return VacancyDto.builder()
                .id(v.getId())
                .name(v.getName())
                .description(v.getDescription())
                .categoryId(v.getCategory().getId())
                .salary(v.getSalary())
                .expFrom(v.getExpFrom())
                .expTo(v.getExpTo())
                .isActive(v.getIsActive())
                .authorId(v.getAuthor().getId())
                .createdDate(v.getCreatedDate())
                .updateTime(v.getUpdateTime())
                .build();
    }

    @Override
    public ResponseEntity<?> getVacancyById(Long id) {
        Optional<Vacancy> vacancy = vacancyRepository.findVacancyById(id);
        if (vacancy.isPresent()) {
            log.info("Вакансии: найдено id={}", id);
            VacancyDto dto = new VacancyDto();
            dto.setId(vacancy.get().getId());
            dto.setName(vacancy.get().getName());
            dto.setDescription(vacancy.get().getDescription());
            dto.setCategoryId(vacancy.get().getCategory().getId());
            dto.setSalary(vacancy.get().getSalary());
            dto.setExpFrom(vacancy.get().getExpFrom());
            dto.setExpTo(vacancy.get().getExpTo());
            dto.setIsActive(vacancy.get().getIsActive());
            dto.setAuthorId(vacancy.get().getAuthor().getId());
            dto.setCreatedDate(vacancy.get().getCreatedDate());
            dto.setUpdateTime(vacancy.get().getUpdateTime());
            return ResponseEntity.ok(dto);
        } else {
            log.warn("Вакансии: не найдено id={}", id);
            throw new NotFoundException("Vacancy with id=" + id + " not found");
        }
    }

    @Override
    public void deleteVacancyById(Long id) {
        log.warn("Вакансии: удаление id={}", id);
        vacancyRepository.deleteById(id);
    }

    @Override
    public void respondToVacancy(ResponseDto dto, Long userId) {
        log.info("Отклик: vacancyId={}, resumeId={}, userId={}", dto.getVacancyId(), dto.getResumeId(), userId);
        RespondedApplicant respondedApplicant = new RespondedApplicant();
        respondedApplicant.getResume().setId(dto.getResumeId());
        respondedApplicant.getVacancy().setId(dto.getVacancyId());
        respondedApplicant.setConfirmation(false);
        respondedApplicantRepository.save(respondedApplicant);
        log.debug("Отклик: сохранён");
    }

    @Override
    public ResponseEntity<List<RespondedApplicantDto>> getResponsesByVacancy(Long vacancyId) {
        log.debug("Отклики: запрос списка по vacancyId={}", vacancyId);
        List<RespondedApplicant> responded = respondedApplicantRepository.getResponsesByVacancy(vacancyId);
        List<RespondedApplicantDto> dtos = responded.stream()
                .map(entity -> RespondedApplicantDto.builder()
                        .id(entity.getId())
                        .resumeId(entity.getResume().getId())
                        .vacancyId(entity.getVacancy().getId())
                        .confirmation(entity.getConfirmation())
                        .build())
                .toList();
        log.info("Отклики: найдено {}", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public List<VacancyShortDto> getPublicShortVacancies() {
        var list = vacancyRepository.getActiveShortVacancies();
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
        v.getCategory().setId(dto.getCategoryId());
        v.setSalary(dto.getSalary());
        v.setExpFrom(dto.getExpFrom());
        v.setExpTo(dto.getExpTo());
        v.setIsActive(dto.getIsActive());
        vacancyRepository.saveVacancy_IdUser_Id(v, vacancyId, employerId);
        log.info("Вакансия {} отредактирована работодателем {}", vacancyId, employerId);
    }

    @Override
    public void vacancyIsActiveOwned(Long vacancyId, VacancyIsActiveDto dto, Long employerId) {
        log.debug("vacancyIsActiveOwned(vacancyId={}, employerId={})", vacancyId, employerId);
        requireVacancyOwner(vacancyId, employerId);
        vacancyRepository.vacancyIsActive(vacancyId, dto.getIsActive());
        log.info("Статус активности вакансии {} изменён работодателем {} на {}", vacancyId, employerId, dto.getIsActive());
    }

    private void requireVacancyOwner(Long vacancyId, Long employerId) {
        Long ownerId = vacancyRepository.getOwnerId(vacancyId);
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
            dto.setCategoryId(v.getCategory().getId());
            dto.setSalary(v.getSalary());
            dto.setExpFrom(v.getExpFrom());
            dto.setExpTo(v.getExpTo());
            dto.setIsActive(v.getIsActive());
            dto.setAuthorId(v.getAuthor().getId());
            dto.setCreatedDate(v.getCreatedDate());
            dto.setUpdateTime(v.getUpdateTime());
            return dto;
        }).toList();
    }

    @Override
    public List<VacancyDto> findVacanciesById(Long userId) {

        return vacancyRepository.findAllByAuthor_Id((userId))
                .stream().map(v -> {
                    VacancyDto dto = new VacancyDto();
                    dto.setId(v.getId());
                    dto.setName(v.getName());
                    dto.setCategoryId(v.getCategory().getId());
                    dto.setDescription(v.getDescription());
                    dto.setSalary(v.getSalary());
                    dto.setExpFrom(v.getExpFrom());
                    dto.setExpTo(v.getExpTo());
                    dto.setIsActive(v.getIsActive());
                    dto.setUpdateTime(v.getUpdateTime());
                    dto.setCreatedDate(v.getCreatedDate());
                    return dto;
                }).toList();
    }

    @Override
    public List<VacancyDto> findAll(Long categoryId) {

        return vacancyRepository.findVacanciesByCategory_Id(categoryId)
                .stream()
                .map(v -> {
                    VacancyDto dto = new VacancyDto();
                    dto.setId(v.getId());
                    dto.setName(v.getName());
                    dto.setDescription(v.getDescription());
                    dto.setSalary(v.getSalary());
                    dto.setExpFrom(v.getExpFrom());
                    dto.setExpTo(v.getExpTo());
                    dto.setIsActive(v.getIsActive());
                    dto.setUpdateTime(v.getUpdateTime());
                    dto.setCreatedDate(v.getCreatedDate());
                    return dto;
                })
                .toList();
//        return vacancyRepository.findAll()
//                .stream()
//                .map(vacancy ->{
//                    VacancyDto dto = new VacancyDto();
//                    dto.setId(vacancy.getId());
//                    dto.setName(vacancy.getName());
//                    dto.setCategoryId(vacancy.getCategory().getId());
//                    dto.setDescription(vacancy.getDescription());
//                    dto.setSalary(vacancy.getSalary());
//                    dto.setExpFrom(vacancy.getExpFrom());
//                    dto.setExpTo(vacancy.getExpTo());
//                    dto.setIsActive(vacancy.getIsActive());
//                    dto.setUpdateTime(vacancy.getUpdateTime());
//                    dto.setCreatedDate(vacancy.getCreatedDate());
//                    return dto;
//                }).toList();
    }

    @Override
    public List<VacancyDto> findByCategory(Long categoryId) {
        return vacancyRepository.findByCategory_Id(categoryId);
    }

    @Override
    public List<VacancyDto> findByEmployer(Long employerId) {
        return vacancyRepository.findAllByAuthorId(employerId);
    }
//
//    Override
//    public List<ResumeDto> findResumesById(Long applicantId) {
//        return resumeRepository.findAllByApplicant_Id(applicantId)
//                .stream().map(r -> {
//                    ResumeDto dto = new ResumeDto();
//                    dto.setApplicantId(r.getApplicant().getId());
//                    dto.setName(r.getName());
//                    dto.setCategoryId(r.getCategory().getId());
//                    dto.setSalary(r.getSalary());
//                    dto.setActive(r.getIsActive());
//                    dto.setCreatedDate(r.getCreatedDate());
//                    dto.setUpdateTime(r.getUpdateTime());
//                    return dto;
//                }).toList();
//
//    }
}
