package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.ChatMessageDto;
import kg.attractor.job_search_java_25.dto.messageDto.MessageDto;
import kg.attractor.job_search_java_25.service.ChatService;
import kg.attractor.job_search_java_25.service.RespondService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatWsController {
    private final ChatService chatService;
    private final UserService userService;
    private final SimpMessagingTemplate template;
    private final RespondService respondService;



    @MessageMapping("/chat/{responseId}/send")
    public void send(@DestinationVariable Long responseId, @Payload MessageDto dto) {
        Long userId = userService.getRequiredUserId();
        MessageDto saved = chatService.sendMessage(responseId, userId, dto);
        String topic = "/topic/chat." + responseId;
        log.debug("Broadcast to {} messageId={}", topic, saved.getId());
        template.convertAndSend(topic, saved);
    }
}
