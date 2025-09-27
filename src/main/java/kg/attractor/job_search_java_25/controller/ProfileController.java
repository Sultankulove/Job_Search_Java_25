package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.ActiveDto;
import kg.attractor.job_search_java_25.dto.publication.PublicationListItemDto;
import kg.attractor.job_search_java_25.dto.userDtos.AvatarDto;
import kg.attractor.job_search_java_25.dto.userDtos.EditProfileDto;
import kg.attractor.job_search_java_25.service.ProfileService;
import kg.attractor.job_search_java_25.service.PublicationService;
import kg.attractor.job_search_java_25.service.ResumeService;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final PublicationService publicationService;


    @GetMapping("/profile/publications")
    public String myPublications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, name = "q") String term,
            Model model
    ) {
        Long userId = userService.getRequiredUserId();
        Page<PublicationListItemDto> pubPage =
                publicationService.findMyPublications(userId, PageRequest.of(page, size), sort, term);

        model.addAttribute("page", pubPage);
        model.addAttribute("q", term);
        model.addAttribute("sort", sort);
        return "profile/my-publications";
    }

    @GetMapping
    public String profile(Model model, Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        Long userId = userService.findUserIdByEmail(auth.getName());
        var user = profileService.getMyProfile(userId);
        model.addAttribute("user", user);

        boolean isEmployer = auth.getAuthorities().stream().anyMatch(a -> "ROLE_EMPLOYER".equals(a.getAuthority()));
        boolean isApplicant = auth.getAuthorities().stream().anyMatch(a -> "ROLE_APPLICANT".equals(a.getAuthority()));
        model.addAttribute("isEmployer", isEmployer);
        model.addAttribute("isApplicant", isApplicant);

        if (isEmployer) {
            model.addAttribute("myVacancies", profileService.getMyShortVacancies(userId));
        }
        if (isApplicant) {
            model.addAttribute("myResumes", profileService.getMyShortResumes(userId));
        }
        model.addAttribute("myPublications", publicationService.findRecentByAuthor(userId, 5));
        return "profile";
    }


    @PostMapping("avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile avatar, Authentication authentication) {
        Long authId = userService.findUserIdByEmail(authentication.getName());
        profileService.addAvatar(new AvatarDto(avatar, authId));
        return "redirect:/profile/edit";
    }


    @GetMapping("/edit")
    public String editProfile(Model model, Principal principal) {
        if (principal == null) return "redirect:/auth/login";
        if (!model.containsAttribute("dto")) {
            Long id = userService.findUserIdByEmail(principal.getName());
            var u = profileService.getMyProfile(id);
            model.addAttribute("dto", EditProfileDto.builder()
                    .name(u.getName()).surname(u.getSurname()).age(u.getAge())
                    .email(u.getEmail()).phoneNumber(u.getPhoneNumber()).build());
        }
        return "edit_profile";
    }


    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("dto") @Valid EditProfileDto dto,
                                BindingResult br, Principal principal) {
        if (br.hasErrors()) return "edit_profile";
        Long authId = userService.findUserIdByEmail(principal.getName());
        profileService.updateProfileByUserId(dto, authId);
        return "redirect:/profile";
    }

    @PatchMapping("/resume/{id}/touch")
    @ResponseBody
    public ResponseEntity<Void> touchResume(@PathVariable Long id, Authentication auth) {
        Long uid = userService.findUserIdByEmail(auth.getName());
        resumeService.updateTimeOwned(id, uid);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/resume/{id}/publish")
    @ResponseBody
    public ResponseEntity<Void> publishResume(@PathVariable Long id,
                                              @RequestParam("active") boolean active,
                                              Authentication auth) {
        Long uid = userService.findUserIdByEmail(auth.getName());
        resumeService.resumeIsActiveOwned(id, new ActiveDto(active), uid);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/vacancy/{id}/touch")
    @ResponseBody
    public ResponseEntity<Void> touchVacancy(@PathVariable Long id, Authentication auth) {
        Long uid = userService.findUserIdByEmail(auth.getName());
        vacancyService.touchOwned(id, uid);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/vacancy/{id}/publish")
    @ResponseBody
    public ResponseEntity<Void> publishVacancy(@PathVariable Long id,
                                               @RequestParam("active") boolean active,
                                               Authentication auth) {
        Long uid = userService.findUserIdByEmail(auth.getName());
        vacancyService.setActiveOwned(id, new ActiveDto(active), uid);
        return ResponseEntity.noContent().build();
    }
}
