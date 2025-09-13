package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.service.ProfileService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping
    public String profile(Model model, Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        Long userId = userService.findUserIdByEmail(auth.getName());
        UserProfileDto user = profileService.getMyProfile(userId);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("avatar")
    public String uploadAvatar(
            @RequestPart("avatar") MultipartFile avatar,
            Authentication authentication
    ) {
        Long authId = userService.findUserIdByEmail(authentication.getName());
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
        profileService.updateProfileByUserId(dto, authId);
        return "redirect:/profile";
    }
}
