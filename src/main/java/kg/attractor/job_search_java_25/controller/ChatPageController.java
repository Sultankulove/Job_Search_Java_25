package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.messageDto.MessageDto;
import kg.attractor.job_search_java_25.service.ChatService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatPageController {
    private final ChatService chatService;
    private final UserService userService;

    @GetMapping("/chat/{responseId}")
    public String chatPage(@PathVariable Long responseId, Model m) {
        Long userId = userService.getRequiredUserId();
        List<MessageDto> history = chatService.getChatByResponseId(responseId, userId);
        m.addAttribute("responseId", responseId);
        m.addAttribute("messages", history);
        return "chat";
    }
}
