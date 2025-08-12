package kg.attractor.job_search_java_25.controller.api;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.service.ResumeService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/resumes")
public class ApiResumeController {
    private final ResumeService resumeService;
    private final UserService userService;

    @PatchMapping("{id}")
    public ResponseEntity<?> updateResumeById(@PathVariable Long id, Authentication authentication) {
        Long applicantId = userService.findUserIdByEmail(authentication.getName());
        log.info("PATCH /api/resumes/{} — обновление времени", id);
        resumeService.updateTime(id);
        resumeService.updateTimeOwned(id, applicantId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{resumeId}")
    public ResponseEntity<?> editResumeById(@PathVariable Long resumeId,
                                            @RequestBody @Valid ResumeEditDto resumeEditDto,
                                            Authentication authentication) {
        Long applicantId = userService.findUserIdByEmail(authentication.getName());
        log.info("PUT /api/resumes/{} — редактирование резюме, applicantId={}", resumeId, applicantId);
        resumeService.editResume(resumeEditDto, resumeId, applicantId);
        resumeService.editResumeOwned(resumeEditDto, resumeId, applicantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{resumeId}/status")
    public ResponseEntity<?> resumesIsActiveById(@PathVariable Long resumeId,
                                                 @RequestBody @Valid ResumeIsActiveDto resumeIsActiveDto,
                                                 Authentication authentication) {
        Long applicantId = userService.findUserIdByEmail(authentication.getName());
        log.info("PATCH /api/resumes/{}/status — публикация={}", resumeId, resumeIsActiveDto.getIsActive());
        resumeService.resumeIsActiveById(resumeId, resumeIsActiveDto);
        resumeService.resumeIsActiveOwned(resumeId, resumeIsActiveDto, applicantId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ResumeEditDto> createResume(@RequestBody @Valid ResumeEditDto resumeEditDto,
                                                      Authentication authentication) {
        Long applicantId = userService.findUserIdByEmail(authentication.getName());
        log.info("POST /api/resumes — создание резюме, applicantId={}", applicantId);
        ResumeEditDto saved = resumeService.createResume(applicantId, resumeEditDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getResumeById(@PathVariable Long id) {
        log.debug("GET /api/resumes/{} — получить резюме", id);
        return resumeService.getResumeById(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteResumeById(@PathVariable Long id) {
        log.warn("DELETE /api/resumes/{} — удаление резюме", id);
        resumeService.deleteResumeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResumeDto>> searchResumes(@ModelAttribute ResumeSearchDto criteria) {
        log.debug("GET /api/resumes/search — критерии={}", criteria);
        List<ResumeDto> results = resumeService.searchResumes(criteria);
        return ResponseEntity.ok(results);
    }



}
