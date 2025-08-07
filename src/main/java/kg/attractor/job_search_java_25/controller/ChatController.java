package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.MessageDto;
import kg.attractor.job_search_java_25.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ChatController {
    @PostMapping("responses")
    public String respondToVacancy(@RequestBody ResponseDto dto) {
        // Отклик на вакансию
        return "OK";
    }

    @GetMapping("vacancies/{id}/responses")
    public String getResponsesByVacancy(@PathVariable Long id) {
        // Получить список откликов на вакансию
        return "OK";
    }

    @GetMapping("chat/{responseId}")
    public String getChat(@PathVariable Long responseId) {
        // Создать/получить чат по отклику
        return "OK";
    }

    @PostMapping("chat/{chatId}/message")
    public String sendMessage(@PathVariable Long chatId, @RequestBody MessageDto dto) {
        // Отправить сообщение
        return "OK";
    }




}
