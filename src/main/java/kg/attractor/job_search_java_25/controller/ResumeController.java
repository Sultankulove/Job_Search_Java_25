package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.dto.ResumeListDto;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    // Список всех резюме
    @GetMapping("resumes")
    public ResponseEntity<List<ResumeDto>> allResume() {
        resumeService.findAllResume();
        return ResponseEntity.ok().body(resumeService.findAllResume());
    }

    // Конкретное резюме
    @GetMapping("resumes/{id}")
    public ResponseEntity<ResumeDto> getResume(@PathVariable Long id) {
//        return ResponseEntity.ok(resumeService.getResumeById(id));
        return ResponseEntity.ok(resumeService.getResumeById(id).getBody());
    }

    // Создать резюме. ОК
    @PostMapping("resumes")
    public ResponseEntity<ResumeDto> createResume(@RequestBody ResumeDto resumeDto) {
        resumeService.createResume(resumeDto);
        return ResponseEntity.ok().body(resumeDto);
    }

    // редактировать резюме
    @PutMapping("resumes/{id}")
    public ResponseEntity<ResumeDto> editResume(@PathVariable long id, @RequestBody ResumeDto resumeDto) {
        resumeService.editResume(id, resumeDto);
        return ResponseEntity.ok().body(resumeDto);
    }

    // обновляет резюме(только время)
    @PutMapping("resumes/{id}/update")
    public ResponseEntity<ResumeDto> updateResume(@PathVariable long id, @RequestBody ResumeDto resumeDto) {
        resumeService.updateResume(id, resumeDto);
        // Обновляет только update_time
        return ResponseEntity.ok().body(resumeDto);
    }

    // удаляет резюме
    @DeleteMapping("resumes/{id}")
    public HttpStatus deleteResume(@PathVariable long id) {
        resumeService.deleteResume(id);
        return HttpStatus.OK;
    }

    /// /////////

    @GetMapping("resume/{applicantId}")
    public ResponseEntity<List<ResumeListDto>> listOfCreatedResume(@PathVariable long applicantId) {
        return resumeService.listOfCreatedResume(applicantId);

    }


    // OK
    @GetMapping("resume/categoriesByName/{name}")
    public ResponseEntity<List<ResumeDto>> findResumeByCategoryName(@PathVariable String name) {
        resumeService.findResumeByCategoryName(name);
        return ResponseEntity.ok().body(resumeService.findResumeByCategoryName(name));
    }

    // OK
    @GetMapping("resume/categoriesById/{id}")
    public ResponseEntity<List<ResumeDto>> findResumeByCategoryId(@PathVariable long id) {
        resumeService.findResumeByCategoryId(id);
        return ResponseEntity.ok().body(resumeService.findResumeByCategoryId(id));
    }
}
