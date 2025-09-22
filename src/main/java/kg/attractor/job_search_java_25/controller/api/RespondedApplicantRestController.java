package kg.attractor.job_search_java_25.controller.api;


import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantUpsertDto;
import kg.attractor.job_search_java_25.service.RespondedApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responds")
@RequiredArgsConstructor
public class RespondedApplicantRestController {

    private final RespondedApplicantService respondedApplicantService;

    // Получить все отклики
    @GetMapping
    public ResponseEntity<List<RespondedApplicantDto>> getAll() {
        return ResponseEntity.ok(respondedApplicantService.getAll());
    }

    // Получить отклик по id
    @GetMapping("/{id}")
    public ResponseEntity<RespondedApplicantDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(respondedApplicantService.getById(id));
    }

    // Создать отклик
    @PostMapping
    public ResponseEntity<RespondedApplicantDto> create(@RequestBody RespondedApplicantUpsertDto dto) {
        return ResponseEntity.ok(respondedApplicantService.create(dto));
    }

    // Подтвердить/обновить отклик
    @PutMapping("/{id}")
    public ResponseEntity<RespondedApplicantDto> update(
            @PathVariable Long id,
            @RequestBody RespondedApplicantUpsertDto dto) {
        return ResponseEntity.ok(respondedApplicantService.update(id, dto));
    }

    // Удалить отклик
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        respondedApplicantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vacancy/{vacancyId}")
    public ResponseEntity<List<RespondedApplicantDto>> listByVacancy(@PathVariable Long vacancyId) {
        return ResponseEntity.ok(respondedApplicantService.listByVacancy(vacancyId));
    }

    @GetMapping("/resume/{resumeId}")
    public ResponseEntity<List<RespondedApplicantDto>> listByResume(@PathVariable Long resumeId) {
        return ResponseEntity.ok(respondedApplicantService.listByResume(resumeId));
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> exists(@RequestParam Long resumeId, @RequestParam Long vacancyId) {
        return ResponseEntity.ok(respondedApplicantService.alreadyResponded(resumeId, vacancyId));
    }
}