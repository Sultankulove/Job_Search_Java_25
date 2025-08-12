package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.RegistrationRequestDto;
import kg.attractor.job_search_java_25.dto.UserProfileDto;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;

    @GetMapping
    public String showProfile(Model model, Authentication auth) {
        RegistrationRequestDto dto = new RegistrationRequestDto();
        if (auth != null) {
            dto.setEmail(auth.getName());
        }
        model.addAttribute("user", dto);
        return "profile";
    }

    @GetMapping("/edit")
    public String editProfile(Model model) {
        model.addAttribute("user", new UserProfileDto());
        return "edit_profile";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("user") @Valid UserProfileDto profileDto, BindingResult bindingResult, @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile, Model model, Authentication auth) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "edit_profile";
        }

        return "redirect:/profile";
    }
}
