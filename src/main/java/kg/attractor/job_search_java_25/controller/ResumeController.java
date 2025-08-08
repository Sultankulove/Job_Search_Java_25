package kg.attractor.job_search_java_25.controller;

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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/resumes")
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;

    @PatchMapping("{Id}")
    public ResponseEntity<Void> updateResumeById(@PathVariable Long id, Authentication authentication) {
        Long applicantId = userService.findUserIdByEmail(authentication.getName());
        log.info("PATCH /api/resumes/{} — обновление времени", id);
        resumeService.updateTime(id);
        resumeService.updateTimeOwned(id, applicantId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{resumeId}")
    public ResponseEntity<ResumeEditDto> editResumeById(@PathVariable Long resumeId, @RequestBody @Valid ResumeEditDto resumeEditDto, Authentication authentication) {
        Long applicantId = userService.findUserIdByEmail(authentication.getName());

        log.info("PUT /api/resumes/{} — редактирование резюме, applicantId={}", resumeId, applicantId);

        resumeService.editResume(resumeEditDto, resumeId, applicantId);
        resumeService.editResumeOwned(resumeEditDto, resumeId, applicantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{resumeId}/status")
    public ResponseEntity<ResumeIsActiveDto> resumesIsActiveById(@PathVariable Long resumeId, @RequestBody @Valid ResumeIsActiveDto resumeIsActiveDto, Authentication authentication) {
        Long applicantId = userService.findUserIdByEmail(authentication.getName());

        log.info("PATCH /api/resumes/{}/status — публикация={}, запрос", resumeId, resumeIsActiveDto.getIsActive());

        resumeService.resumeIsActiveById(resumeId, resumeIsActiveDto);
        resumeService.resumeIsActiveOwned(resumeId, resumeIsActiveDto, applicantId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ResumeEditDto> createResume(@RequestBody @Valid ResumeEditDto resumeEditDto,  Authentication authentication) {
        Long applicantId = userService.findUserIdByEmail(authentication.getName());

        log.info("POST /api/resumes — создание резюме, applicantId={}", applicantId);

        ResumeEditDto saved = resumeService.createResume(applicantId, resumeEditDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

    }

    @GetMapping("{id}")
    public ResponseEntity<ResumeDto> getResumeById(@PathVariable Long id) {
        log.debug("GET /api/resumes/{} — получить резюме", id);

        return resumeService.getResumeById(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteResumeById(@PathVariable Long id) {

        log.warn("DELETE /api/resumes/{} — удаление резюме", id);
        resumeService.deleteResumeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public String searchResumes(@RequestParam Map<String, String> params) {

        // Поиск резюме по фильтрам (для работодателя)

        log.debug("GET /api/resumes/search — фильтры={}", params);
        return "OK";
    }



}
