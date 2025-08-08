package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping
    public ResponseEntity<List<ResumeShortDto>> listOfCreatedResumes() {
        // Видны только владельцу профиля
        // Вывод: List {Название резюме и Дата обновления резюме.}

        Long applicantId = 1L; // Хард-код
        // Должен получить user(id) владельца профиля и передать
        var shortResumesList = resumeService.getShortResumesList(applicantId);
        return ResponseEntity.ok(shortResumesList);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updateResumeById(@PathVariable Long resumeId) {
        // Обновить (Только его дату обновления)
        resumeService.updateTime(resumeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{resumeId}")
    public ResponseEntity<ResumeEditDto> editResumeById(@PathVariable Long resumeId, @RequestBody ResumeEditDto resumeEditDto) {
        // Редактировать

        // Нужно взять id пользователя(auth) и передать как applicant_id
        Long applicantId = 1L; // хард-код

        resumeService.editResume(resumeEditDto, resumeId, applicantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{resumeId}/status")
    public ResponseEntity<ResumeIsActiveDto> resumesIsActiveById(@PathVariable Long resumeId, @RequestBody ResumeIsActiveDto resumeIsActiveDto) {
        // Опубликован или не опубликован резюме
        // Продумать надо

        resumeService.resumeIsActiveById(resumeId, resumeIsActiveDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ResumeEditDto> createResume(@RequestBody ResumeEditDto resumeEditDto) {

        // Создать резюме:
        // applicantId передает через authentication
        // name, category(categoryId), salary, isActive


        Long applicantId = 1L;
        ResumeEditDto saved = resumeService.createResume(applicantId, resumeEditDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

    }

    @GetMapping("{id}")
    public ResponseEntity<ResumeDto> getResumeById(@PathVariable Long id) {
        // Получение одного резюме по id
        return resumeService.getResumeById(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteResumeById(@PathVariable Long id) {
        // Удаление резюме
        resumeService.deleteResumeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public String searchResumes(@RequestParam Map<String, String> params) {
        // Поиск резюме по фильтрам (для работодателя)
        return "OK";
    }



}
