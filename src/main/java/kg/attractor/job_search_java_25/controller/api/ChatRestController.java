package kg.attractor.job_search_java_25.controller.api;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.ResponseDto;
import kg.attractor.job_search_java_25.dto.messageDto.MessageDto;
import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.service.ChatService;
import kg.attractor.job_search_java_25.service.RespondService;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api")
public class ChatRestController {
    private final VacancyService vacancyService;
    private final ChatService chatService;
    private final UserService userService;
    private final RespondService respondService;

    @PostMapping("responses")
    public ResponseEntity<Void> respondToVacancy(@RequestBody @Valid ResponseDto dto,
                                                 Authentication auth) {
        Long applicantId = userService.findUserIdByEmail(auth.getName());
        log.info("POST /api/responses — отклик, applicantId={}", applicantId);
        vacancyService.respondToVacancy(dto, applicantId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("vacancies/{vacancyId}/responses")
    public ResponseEntity<List<RespondedApplicantDto>> getResponsesByVacancy(@PathVariable Long vacancyId,
                                                                             Authentication auth) {
        Long employerId = userService.findUserIdByEmail(auth.getName());
        log.debug("GET /api/vacancies/{}/responses — employerId={}", vacancyId, employerId);
        return ResponseEntity.ok(vacancyService.getResponsesByVacancy(vacancyId, employerId));
    }

    @GetMapping("chat/{responseId}")
    public ResponseEntity<List<MessageDto>> getChat(@PathVariable Long responseId, Authentication auth) {
        Long userId = userService.findUserIdByEmail(auth.getName());
        log.debug("GET /api/chat/{} — userId={}", responseId, userId);
        return ResponseEntity.ok(chatService.getChatByResponseId(responseId, userId));
    }

    @PostMapping("chat/{responseId}/message")
    public ResponseEntity<Void> sendMessage(@PathVariable Long responseId,
                                            @RequestBody @Valid MessageDto dto,
                                            Authentication auth) {
        Long userId = userService.findUserIdByEmail(auth.getName());
        log.info("POST /api/chat/{}/message — userId={}", responseId, userId);
        chatService.sendMessage(responseId, userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("chat/unread-count")
    public Map<String, Long> unreadCount(Authentication authentication) {
        return Map.of("count", 0L);
    }

    @GetMapping("respond/pending-count")
    public Map<String, Long> pendingResponses(Authentication authentication) {
        Long employerId = userService.findUserIdByEmail(authentication.getName());
        long count = respondService.listResponsesForEmployer(employerId).size();
        return Map.of("count", count);
    }

    @GetMapping("respond/answers-count")
    public Map<String, Long> applicantResponses(Authentication authentication) {
        Long applicantId = userService.findUserIdByEmail(authentication.getName());
        long count = respondService.listResponsesForApplicant(applicantId).size();
        return Map.of("count", count);
    }
}