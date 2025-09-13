package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.VacancyDao;
import kg.attractor.job_search_java_25.dto.ActiveDto;
import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.ResponseDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyUpsertDto;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.mappers.RespondedApplicantMapper;
import kg.attractor.job_search_java_25.mappers.VacancyMapper;
import kg.attractor.job_search_java_25.model.Category;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.repository.*;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final RespondedApplicantRepository respondedApplicantRepository;
    private final VacancyMapper vacancyMapper;
    private final RespondedApplicantMapper respondedApplicantMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;

    @Override
    public Page<VacancyListItemDto> getShortVacanciesList(Long authorId, Pageable pageable) {
        return vacancyRepository.getVacanciesByAuthor_Id(authorId, pageable);
    }

    @Override
    public ResponseEntity<?> updateTime(Long id) {
        log.info("Вакансии: обновление времени id={}", id);
        vacancyRepository.updateTime(id);
        return ResponseEntity.noContent().build();
    }


    @Override
    public void edtVacancy(VacancyUpsertDto v, Long vacancyId, Long userId) {


        vacancyRepository.saveVacancy_IdUser_Id(
                vacancyMapper.applyUpsert(
                        v,
                        vacancyRepository.findById(vacancyId)
                                .orElseThrow(() -> new ForbiddenException("Нет вакансии с id" + vacancyId)),
                        categoryRepository.getCategoryById(v.getCategoryId()),
                        userRepository.findById(userId)
                                .orElseThrow(() -> new NotFoundException("User not found"))
                ),
                vacancyId,
                userId
        );
    }

    @Override
    public void vacancyIsActive(Long vacancyId, ActiveDto activeDto) {
        vacancyRepository.vacancyIsActive(vacancyId, activeDto.getActive());
    }


    @Override
    public void createVacancy(VacancyUpsertDto v, Long userId) {

        vacancyRepository.save(
                vacancyMapper.applyUpsert(v,
                        new Vacancy(),
                        categoryRepository.getCategoryById(v.getCategoryId()),
                        userRepository.findUserById(userId)
                                .orElseThrow(() -> new NotFoundException("User not found"))
                )
        );
    }

    @Override
    public ResponseEntity<?> findVacancyById(Long id) {
        return ResponseEntity.ok(
                vacancyMapper.toListItem(
                        vacancyRepository.findVacancyById(id)
                                .orElseThrow(() -> new NotFoundException("Vacancy not found"))
                )
        );
    }


    @Override
    public void deleteVacancyById(Long id) {
        vacancyRepository.deleteById(id);
    }


    @Override
    public void respondToVacancy(ResponseDto dto) {
        respondedApplicantRepository.save(
                respondedApplicantMapper.applyUpsert(
                        new RespondedApplicant(),
                        vacancyRepository.getVacancyById(dto.getVacancyId()),
                        resumeRepository.getResumeById(dto.getResumeId()),
                        false)
        );
    }

    @Override
    public ResponseEntity<List<RespondedApplicantDto>> getResponsesByVacancy(Long vacancyId) {
        List<RespondedApplicant> responded = respondedApplicantRepository.getResponsesByVacancy(vacancyId);
        List<RespondedApplicantDto> dtos = responded.stream()
                .map(entity -> RespondedApplicantDto.builder()
                        .id(entity.getId())
                        .resumeId(entity.getResume().getId())
                        .vacancyId(entity.getVacancy().getId())
                        .confirmation(entity.getConfirmation())
                        .build())
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public List<VacancyShortDto> getPublicShortVacancies() {
        var list = vacancyRepository.getActiveShortVacancies();
        return list;
    }

    @Override
    public void editVacancyOwned(VacancyEditDto dto, Long vacancyId, Long employerId) {
        requireVacancyOwner(vacancyId, employerId);
        Vacancy v = new Vacancy();
        v.setName(dto.getName());
        v.setDescription(dto.getDescription());
        v.getCategory().setId(dto.getCategoryId());
        v.setSalary(dto.getSalary());
        v.setExpFrom(dto.getExpFrom());
        v.setExpTo(dto.getExpTo());
        v.setIsActive(dto.isActive());
        vacancyRepository.saveVacancy_IdUser_Id(v, vacancyId, employerId);
    }

    @Override
    public void vacancyIsActiveOwned(Long vacancyId, VacancyIsActiveDto dto, Long employerId) {
        requireVacancyOwner(vacancyId, employerId);
        vacancyRepository.vacancyIsActive(vacancyId, dto.getIsActive());
    }

    private void requireVacancyOwner(Long vacancyId, Long employerId) {
        Long ownerId = vacancyRepository.getOwnerId(vacancyId);
        if (!ownerId.equals(employerId)) throw new ForbiddenException("Not your vacancy");
    }

    @Override
    public List<VacancyDto> searchVacancies(VacancySearchDto criteria) {
        List<Vacancy> found = vacancyDao.searchVacancies(criteria);
        return found.stream().map(VacancyMapper::toDto).toList();
    }

    @Override
    public List<VacancyDto> findVacanciesById(Long userId) {

        return vacancyRepository.findAllByAuthor_Id((userId))
                .stream().map(VacancyMapper::toDto).toList();
    }

    @Override
    public List<VacancyDto> findByEmployer(Long employerId) {
        return vacancyRepository.findAllByAuthorId(employerId);
    }

    @Override
    public List<VacancyDto> findAllVacancies() {
        return vacancyRepository.findAll()
                .stream()
                .map(VacancyMapper::toDto).toList();
    }

    @Override
    public Page<VacancyDto> getVacancies(Pageable pageable) {
        return vacancyRepository.findAll(pageable)
                .map(VacancyMapper::toDto);
    }

    @Override
    public Page<VacancyDto> getVacanciesByCategory(Long categoryId, PageRequest of) {
        return vacancyRepository.findByCategory_Id(categoryId, of)
                .map(VacancyMapper::toDto);
    }

    @Override
    public Page<VacancyDto> findByEmployerId(Long employerId, Pageable pageable) {

        return vacancyRepository.findAllByAuthor_Id(employerId, pageable)
                .map(VacancyMapper::toDto);
    }

    @Override
    public Page<VacancyDto> findByEmployerIdAndCategory(Long employerId, Long categoryId, Pageable pageable) {
        return vacancyRepository.findAllByAuthor_IdAndCategory_Id(employerId, categoryId, pageable)
                .map(VacancyMapper::toDto);
    }



}
