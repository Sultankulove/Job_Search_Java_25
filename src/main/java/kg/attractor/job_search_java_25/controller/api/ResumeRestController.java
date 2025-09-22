package kg.attractor.job_search_java_25.controller.api;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeUpsertDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeViewDto;
import kg.attractor.job_search_java_25.service.ResumeService;
import kg.attractor.job_search_java_25.service.UserService;
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
@RequestMapping("api/resumes")
public class ResumeRestController {
    private final ResumeService resumeService;
    private final UserService userService;

    @PatchMapping("{id}/touch")
    public ResponseEntity<Void> touch(@PathVariable Long id, Authentication auth) {
        Long applicantId = userService.findUserIdByEmail(auth.getName());
        log.info("PATCH /api/resumes/{}/touch — обновление времени владельцем {}", id, applicantId);
        resumeService.updateTimeOwned(id, applicantId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ResumeViewDto> edit(@PathVariable Long id,
                                              @RequestBody @Valid ResumeUpsertDto body,
                                              Authentication auth) {
        Long applicantId = userService.findUserIdByEmail(auth.getName());
        log.info("PUT /api/resumes/{} — редактирование, applicantId={}", id, applicantId);
        resumeService.editResumeOwned(body, id, applicantId);
        return ResponseEntity.ok(resumeService.getResumeById(id));
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<Void> setActive(@PathVariable Long id,
                                          @RequestBody @Valid ActiveDto dto,
                                          Authentication auth) {
        Long applicantId = userService.findUserIdByEmail(auth.getName());
        log.info("PATCH /api/resumes/{}/status — active={}, applicantId={}", id, dto.getActive(), applicantId);
        resumeService.resumeIsActiveOwned(id, dto, applicantId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ResumeViewDto> create(@RequestBody @Valid ResumeUpsertDto body,
                                                Authentication auth) {
        Long applicantId = userService.findUserIdByEmail(auth.getName());
        log.info("POST /api/resumes — создание, applicantId={}", applicantId);
        var saved = resumeService.saveResume(applicantId, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("{id}")
    public ResumeViewDto getOne(@PathVariable Long id) {
        log.debug("GET /api/resumes/{} — получить резюме", id);
        return resumeService.getResumeById(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        Long applicantId = userService.findUserIdByEmail(auth.getName());
        log.warn("DELETE /api/resumes/{} — удаление, by={}", id, applicantId);
        resumeService.deleteOwned(id, applicantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<ResumeListItemDto> myResumes(Authentication auth,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "15") int size) {
        Long applicantId = userService.findUserIdByEmail(auth.getName());
        Pageable pageable = PageRequest.of(page, size);
        log.debug("GET /api/resumes?page={}&size={} — мои резюме, applicantId={}", page, size, applicantId);
        return resumeService.getResumesByAuthor(applicantId, pageable).getContent();
    }
}