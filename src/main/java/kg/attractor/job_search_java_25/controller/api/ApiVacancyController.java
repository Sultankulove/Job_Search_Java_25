package kg.attractor.job_search_java_25.controller.api;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancySearchDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyUpsertDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyViewDto;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/vacancies")
public class ApiVacancyController {

    private final VacancyService vacancyService;
    private final UserService userService;

    @GetMapping
    public List<VacancyListItemDto> list(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        Pageable p = PageRequest.of(page, size);
        log.debug("GET /api/vacancies?page={}&size={}", page, size);
        return vacancyService.findList(null, p).getContent();
    }


    @PatchMapping("{vacancyId}/touch")
    public ResponseEntity<Void> touch(@PathVariable Long vacancyId, Authentication auth) {
        Long authorId = userService.findUserIdByEmail(auth.getName());
        log.info("PATCH /api/vacancies/{}/touch — authorId={}", vacancyId, authorId);
        vacancyService.touchOwned(vacancyId, authorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{vacancyId}")
    public ResponseEntity<VacancyViewDto> edit(@PathVariable Long vacancyId,
                                               @RequestBody @Valid VacancyUpsertDto body,
                                               Authentication auth) {
        Long authorId = userService.findUserIdByEmail(auth.getName());
        log.info("PUT /api/vacancies/{} — authorId={}", vacancyId, authorId);
        vacancyService.editVacancyOwned(body, vacancyId, authorId);
        return ResponseEntity.ok(vacancyService.getById(vacancyId));
    }

    @PatchMapping("{vacancyId}/status")
    public ResponseEntity<Void> setActive(@PathVariable Long vacancyId,
                                          @RequestBody @Valid ActiveDto dto,
                                          Authentication auth) {
        Long authorId = userService.findUserIdByEmail(auth.getName());
        log.info("PATCH /api/vacancies/{}/status — active={}, authorId={}", vacancyId, dto.getActive(), authorId);
        vacancyService.setActiveOwned(vacancyId, dto, authorId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<VacancyViewDto> create(@RequestBody @Valid VacancyUpsertDto body,
                                                 Authentication auth) {
        Long authorId = userService.findUserIdByEmail(auth.getName());
        log.info("POST /api/vacancies — authorId={}", authorId);
        var saved = vacancyService.create(body, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("{id}")
    public VacancyViewDto getOne(@PathVariable Long id) {
        log.debug("GET /api/vacancies/{}", id);
        return vacancyService.getById(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        Long authorId = userService.findUserIdByEmail(auth.getName());
        log.warn("DELETE /api/vacancies/{} — by authorId={}", id, authorId);
        vacancyService.deleteOwned(id, authorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("search")
    public List<VacancyListItemDto> search(@ModelAttribute VacancySearchDto criteria) {
        log.debug("GET /api/vacancies/search — criteria={}", criteria);
        return vacancyService.search(criteria);
    }
}