package kg.attractor.job_search_java_25.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ResumeController {

    @GetMapping("resumes/list")
    public String listOfCreatedResumes() {
        // Видны только владельцу профиля
        // Вывод: List {Название резюме и Дата обновления резюме.}
        return "OK";
    }

    @PatchMapping("resumes/{id}")
    public String updateResumeById(@PathVariable Long id) {

        // Обновить (Только его дату обновления)
        return "OK";
    }

    @PutMapping("resumes/{id}")
    public String editResumeById(@PathVariable Long id) {

        // Редактировать
        return "OK";
    }

    @PatchMapping("resumes/{id}/published")
    public String resumesIsActiveById(@PathVariable Long id) {

        // Опубликован или не опубликован резюме
        // Продумать надо
        return "OK";
    }

    @PostMapping("resumes")
    public String createResume() {

        // Создать резюме:
        // applicantId передает через authentication
        // name, category(categoryId), salary, isActive
        // contactsInfo (value) <- contactTypes(type)
        // workExperienceInfo
        // educationInfo


        return "OK";
    }


}
