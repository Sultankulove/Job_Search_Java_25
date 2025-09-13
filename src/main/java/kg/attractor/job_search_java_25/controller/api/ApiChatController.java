package kg.attractor.job_search_java_25.controller.api;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.messageDto.MessageDto;
import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.ResponseDto;
import kg.attractor.job_search_java_25.service.ChatService;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.service.VacancyService;
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
@RequestMapping("api")
public class ApiChatController {
    private final VacancyService vacancyService;
    private final ChatService chatService;
    private final UserService userService;

    @PostMapping("responses")
    public String respondToVacancy(@RequestBody @Valid ResponseDto dto, Authentication authentication) {
        Long userId = userService.findUserIdByEmail(authentication.getName());
        log.info("POST /api/responses — отклик на вакансию, applicantId={}", userId);
        vacancyService.respondToVacancy(dto, userId);

        log.info("Отклик отправлен: vacancyId={}, applicantId={}", dto.getVacancyId(), userId);
        return "OK";
    }

    @GetMapping("vacancies/{vacancyId}/responses")
    public ResponseEntity<List<RespondedApplicantDto>> getResponsesByVacancy(@PathVariable Long vacancyId) {
        log.debug("GET /api/vacancies/{}/responses", vacancyId);
        return vacancyService.getResponsesByVacancy(vacancyId);
    }

    @GetMapping("chat/{responseId}")
    public ResponseEntity<List<MessageDto>> getChat(@PathVariable Long responseId) {

        log.debug("GET /api/chat/{} — получить чат по отклику", responseId);
        List<MessageDto> chat = chatService.getChatByResponseId(responseId);

        log.info("Чат {} сообщений для responseId={}", chat.size(), responseId);
        return ResponseEntity.ok(chat);
    }

    @PostMapping("chat/{chatId}/message")
    public ResponseEntity<String> sendMessage(@PathVariable Long chatId, @RequestBody @Valid MessageDto dto) {
        log.info("POST /api/chat/{}/message — отправка сообщения", chatId);
        chatService.sendMessage(chatId, dto);

        log.debug("Сообщение отправлено в чат={}, длина={}", chatId, dto.getContent() != null ? dto.getContent().length() : 0);
        return ResponseEntity.status(HttpStatus.CREATED).body("Message sent");
    }
}
