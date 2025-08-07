package kg.attractor.job_search_java_25.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/resumes")
public class ResumeController {

    @GetMapping
    public String listOfCreatedResumes() {
        // Видны только владельцу профиля
        // Вывод: List {Название резюме и Дата обновления резюме.}
        return "OK";
    }

    @PatchMapping("{id}")
    public String updateResumeById(@PathVariable Long id) {

        // Обновить (Только его дату обновления)
        return "OK";
    }

    @PutMapping("{id}")
    public String editResumeById(@PathVariable Long id) {

        // Редактировать
        return "OK";
    }

    @PatchMapping("{id}/published")
    public String resumesIsActiveById(@PathVariable Long id) {

        // Опубликован или не опубликован резюме
        // Продумать надо
        return "OK";
    }

    @PostMapping
    public String createResume() {

        // Создать резюме:
        // applicantId передает через authentication
        // name, category(categoryId), salary, isActive
        // contactsInfo (value) <- contactTypes(type)
        // workExperienceInfo
        // educationInfo


        return "OK";
    }

    @GetMapping("{id}")
    public String getResumeById(@PathVariable Long id) {
        // Получение одного резюме по id
        return "OK";
    }


    @DeleteMapping("{id}")
    public String deleteResumeById(@PathVariable Long id) {
        // Удаление резюме
        return "OK";
    }

    @GetMapping("/search")
    public String searchResumes(@RequestParam Map<String, String> params) {
        // Поиск резюме по фильтрам (для работодателя)
        return "OK";
    }



}
