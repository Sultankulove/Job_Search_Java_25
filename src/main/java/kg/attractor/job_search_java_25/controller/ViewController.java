package kg.attractor.job_search_java_25.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeAllInfoDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyViewDto;
import kg.attractor.job_search_java_25.service.ChatService;
import kg.attractor.job_search_java_25.service.ContactTypeService;
import kg.attractor.job_search_java_25.service.RespondService;
import kg.attractor.job_search_java_25.service.ResumeService;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;

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
    public String vacancyView(@PathVariable Long id, Model model, Principal principal) {
        VacancyViewDto dto = vacancyService.getById(id);
        model.addAttribute("v", dto);

        Long userId = userService.getUserIdOrNull();
        String role = userService.getCurrentRole();
        boolean isApplicant = userId != null && "ROLE_APPLICANT".equals(role);
        boolean alreadyResponded = isApplicant && respondService.alreadyResponded(userId, id);

        model.addAttribute("auth", principal != null);
        model.addAttribute("role", role);
        model.addAttribute("responded", alreadyResponded);

        if (isApplicant) {
            model.addAttribute("myResumes", resumeService.getActiveResumesByAuthor(userId));
        }

        if (userId != null && "ROLE_EMPLOYER".equals(role) && userId.equals(dto.getAuthorId())) {
            model.addAttribute("responses", respondService.listResponsesForVacancy(id));
        }
        return "view";
    }

    @PostMapping("/vacancy/{id}/respond")
    @PreAuthorize("hasRole('APPLICANT')")
    public String respond(@PathVariable Long id,
                          @RequestParam("resumeId") Long resumeId,
                          RedirectAttributes redirectAttributes) {
        Long userId = userService.getRequiredUserId();
        if (resumeId == null) {
            redirectAttributes.addFlashAttribute("respondError", "response.resume.required");
            return "redirect:/vacancy/" + id;
        }
        Long responseId = respondService.respond(userId, id, resumeId);
        redirectAttributes.addFlashAttribute("respondSuccess", "response.created");
        return "redirect:/chat/" + responseId;
    }

    @PostMapping("/chat/start/{responseId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public String startChat(@PathVariable Long responseId) {
        Long userId = userService.getRequiredUserId();
        Long chatId = chatService.startChat(responseId, userId);
        return "redirect:/chat/" + chatId;
    }

}
