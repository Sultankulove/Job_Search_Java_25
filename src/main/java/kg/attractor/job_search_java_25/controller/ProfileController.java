package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;
    private final ResponseService responseService;
    private final ProfileService profileService;

//    @GetMapping
//    public String profile(Model model, Authentication auth) {
//    Long userId = userService.findUserIdByEmail(auth.getName());
//    MyProfileDto user = profileService.getMyProfile(userId);
//    model.addAttribute("user", user);
//    return "profile";
//}

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        Long userId = userService.findUserIdByEmail(auth.getName());
        ProfilePageDto user = profileService.get(userId);

        model.addAttribute("user", user);
        model.addAttribute("isEmployer", user.isEmployer());

        if (user.isEmployer()) {
            model.addAttribute("myVacancies", vacancyService.findByEmployer(userId));
            model.addAttribute("myResumes", java.util.List.of());
            model.addAttribute("responsesCount", responseService.countForEmployer(userId));
        } else {
            model.addAttribute("myResumes", resumeService.findByUser(userId));
            model.addAttribute("myVacancies", java.util.List.of());
            model.addAttribute("responsesCount", responseService.countForApplicant(userId));
        }
        return "profile";
    }

    @PostMapping("avatar")
    public String uploadAvatar(
            @RequestPart("avatar") MultipartFile avatar,
            Principal principal
    ) {
        Long authId = userService.findUserIdByEmail(principal.getName());
        AvatarDto avatarDto = new AvatarDto(avatar, authId);


        log.info("POST /api/profile/avatar — загрузка аватара пользователем");
        profileService.addAvatar(avatarDto);

        return "redirect:/profile/edit";
    }


    @GetMapping("/edit")
    public String editProfile(Model model, Principal principal) {
        if (principal == null) return "redirect:/auth/login";
        if (!model.containsAttribute("dto")) {
            Long id = userService.findUserIdByEmail(principal.getName());
            var user = profileService.getMyProfile(id);
            model.addAttribute("dto", new EditProfileDto(
                    user.getName(), user.getSurname(), user.getAge(),
                    user.getEmail(), user.getPhoneNumber()
            ));
    }
        return "edit_profile";
    }


    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("dto") @Valid EditProfileDto dto,
                                BindingResult br,
                                Model model,
                                Principal principal) {
        if (br.hasErrors()) {
            return "edit_profile";
        }
        Long authId = userService.findUserIdByEmail(principal.getName());
        profileService.editProfile(dto, authId);
        return "redirect:/profile";
    }
}
