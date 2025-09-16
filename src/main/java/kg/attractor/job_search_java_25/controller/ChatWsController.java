package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.messageDto.MessageDto;
import kg.attractor.job_search_java_25.service.ChatService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWsController {
    private final ChatService chatService;
    private final UserService userService;
    private final SimpMessagingTemplate template;


    @MessageMapping("/chat/{responseId}/send")
    public void sendMessage(@DestinationVariable Long responseId, MessageDto dto) {
        Long userId = userService.getRequiredUserId();
        MessageDto saved = chatService.sendMessage(responseId, userId, dto);
        template.convertAndSend("/topic/chat." + responseId, saved);
    }
}
