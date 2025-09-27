package kg.attractor.job_search_java_25.controller;

import java.util.List;

import kg.attractor.job_search_java_25.dto.ResponseDto;
import kg.attractor.job_search_java_25.dto.messageDto.MessageDto;
import kg.attractor.job_search_java_25.service.ChatService;
import kg.attractor.job_search_java_25.service.RespondService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatPageController {
    private final ChatService chatService;
    private final UserService userService;
    private final RespondService respondService;

    @GetMapping("/chats")
    public String chats(Model model) {
        Long userId = userService.getRequiredUserId();
        String role = userService.getCurrentRole();
        model.addAttribute("role", role);

        if ("ROLE_APPLICANT".equals(role)) {
            List<ResponseDto> responses = respondService.listResponsesForApplicant(userId);
            model.addAttribute("applicantResponses", responses);
        }
        if ("ROLE_EMPLOYER".equals(role)) {
            List<ResponseDto> responses = respondService.listResponsesForEmployer(userId);
            model.addAttribute("employerResponses", responses);
        }
        return "chats";
    }

    @GetMapping("/chat/{responseId}")
    public String chatPage(@PathVariable Long responseId, Model model) {
        Long userId = userService.getRequiredUserId();
        List<MessageDto> history = chatService.getChatByResponseId(responseId, userId);
        model.addAttribute("responseId", responseId);
        model.addAttribute("messages", history);
        return "chat";
    }
}