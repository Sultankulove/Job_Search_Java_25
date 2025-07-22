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

    @PostMapping("resume")
    public ResponseEntity<ResumeDto> createResume(@RequestBody ResumeDto resumeDto) {
        resumeService.createResume(resumeDto);
        return ResponseEntity.ok().body(resumeDto);
    }

    @PutMapping("resume/{id}")
    public ResponseEntity<ResumeDto> editResume(@PathVariable long id, @RequestBody ResumeDto resumeDto) {
        resumeService.editResume(id, resumeDto);
        return ResponseEntity.ok().body(resumeDto);
    }

    @PutMapping("resume/{id}/update")
    public ResponseEntity<ResumeDto> updateResume(@PathVariable long id, @RequestBody ResumeDto resumeDto) {
        resumeService.updateResume(id, resumeDto);
        // Обновляет только update_time
        return ResponseEntity.ok().body(resumeDto);
    }

    @DeleteMapping("resume/{id}")
    public HttpStatus deleteResume(@PathVariable long id) {
        resumeService.deleteResume(id);
        return HttpStatus.OK;
    }

    @GetMapping("resume")
    public ResponseEntity<ResumeDto> allResume() {
        return resumeService.findAllResume();
    }

    @GetMapping("resume/{applicantId}")
    public ResponseEntity<List<ResumeListDto>> listOfCreatedResume(@PathVariable long applicantId) {
        return resumeService.listOfCreatedResume(applicantId);

    }

    @GetMapping("resume/categories/{name}")
    public ResponseEntity<List<ResumeDto>> findResumeByCategoryName(@PathVariable String name) {
        return resumeService.findResumeByCategoryName(name);
    }

    @GetMapping("resume/categories/{id}")
    public ResponseEntity<List<ResumeDto>> findResumeByCategoryId(@PathVariable long id) {
        return resumeService.findResumeByCategoryId(id);
    }
}
