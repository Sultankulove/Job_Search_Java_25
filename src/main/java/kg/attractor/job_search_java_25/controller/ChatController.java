package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.MessageDto;
import kg.attractor.job_search_java_25.dto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.ResponseDto;
import kg.attractor.job_search_java_25.service.ChatService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ChatController {
    private final VacancyService vacancyService;
    private final ChatService chatService;

    @PostMapping("responses")
    public String respondToVacancy(@RequestBody ResponseDto dto) {
        // Для соискателя
        // Отклик на вакансию
        Long id = 1L;
        vacancyService.respondToVacancy(dto, id);

        return "OK";
    }

    @GetMapping("vacancies/{vacancyId}/responses")
    public ResponseEntity<List<RespondedApplicantDto>> getResponsesByVacancy(@PathVariable Long vacancyId) {
        // Получить список откликов на вакансию
        return vacancyService.getResponsesByVacancy(vacancyId);
    }

    @GetMapping("chat/{responseId}")
    public ResponseEntity<List<MessageDto>> getChat(@PathVariable Long responseId) {
        // Создать/получить чат по отклику
        List<MessageDto> chat = chatService.getChatByResponseId(responseId);
        return ResponseEntity.ok(chat);
    }

    @PostMapping("chat/{chatId}/message")
    public ResponseEntity<String> sendMessage(@PathVariable Long chatId, @RequestBody MessageDto dto) {
        // Отправить сообщение
        chatService.sendMessage(chatId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Message sent");
    }
}
