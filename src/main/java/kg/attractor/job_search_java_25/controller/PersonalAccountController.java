package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.controller.api.ApiProfileController;
import kg.attractor.job_search_java_25.dto.MyProfileDto;
import kg.attractor.job_search_java_25.dto.ResumeShortDto;
import kg.attractor.job_search_java_25.dto.VacancyShortDto;
import kg.attractor.job_search_java_25.service.ProfileService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/personal")
public class PersonalAccountController {
    private final ProfileService profileService;
    private final UserService userService;
    @GetMapping
    public String personalAccount(Model model, Authentication auth) {
//        ResumeShortDto resumeShortDto = new ResumeShortDto();
//        VacancyShortDto vacancyShortDto = new VacancyShortDto();
        Long authId = userService.findUserIdByEmail(auth.getName());
        MyProfileDto myProfileDto = profileService.getMyProfile(authId);
        List<ResumeShortDto> myResumeShortDto = profileService.getMyShortResumes(authId);


        model.addAttribute("myUser", myProfileDto);
        model.addAttribute("resumes", myResumeShortDto);
        return "personalAccount";
    }
}
