package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeAllInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeViewDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos.ContactTypeDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyViewDto;
import kg.attractor.job_search_java_25.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final RespondService respondService;
    private final ChatService chatService;
    private final UserService userService;
    private final ContactTypeService contactTypeService;

    @GetMapping("/resume/{id}")
    public String resumeView(@PathVariable Long id, Model m, Principal principal) {
        ResumeAllInfoDto dto = resumeService.getResumeAllById(id);
        m.addAttribute("r", dto);
        m.addAttribute("contact", contactTypeService.findAll());
        m.addAttribute("auth", principal != null);
        m.addAttribute("role", userService.getCurrentRole());
        return "view";
    }


    @GetMapping("/vacancy/{id}")
    public String vacancyView(@PathVariable Long id, Model m, Principal principal) {
        VacancyViewDto dto = vacancyService.getById(id);
        m.addAttribute("v", dto);
        var userId = userService.getUserIdOrNull();
        boolean alreadyResponded = (userId != null && userService.hasRole("ROLE_APPLICANT"))
                && respondService.alreadyResponded(userId, id);
        m.addAttribute("responded", alreadyResponded);
        m.addAttribute("auth", principal != null);
        m.addAttribute("role", userService.getCurrentRole());

        if (userId != null && userService.hasRole("ROLE_EMPLOYER") && userId.equals(dto.getAuthorId())) {
            m.addAttribute("responses", respondService.listResponsesForVacancy(id));
        }
        return "view";
    }

    @PostMapping("/vacancy/{id}/respond")
    @PreAuthorize("hasRole('APPLICANT')")
    public String respond(@PathVariable Long id) {
        respondService.respond(userService.getRequiredUserId(), id);
        return "redirect:/vacancy/" + id + "?responded";
    }

    @PostMapping("/chat/start/{responseId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public String startChat(@PathVariable Long responseId) {
        Long userId = userService.getRequiredUserId();
        Long chatId = chatService.startChat(responseId, userId);
        return "redirect:/chat/" + chatId;
    }

}
