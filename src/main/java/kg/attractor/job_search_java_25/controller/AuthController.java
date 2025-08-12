package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.RegistrationRequestDto;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model, @ModelAttribute("dto") RegistrationRequestDto dto,
                               org.springframework.security.web.csrf.CsrfToken token) {
        model.addAttribute("_csrf", token);
        model.addAttribute("dto", dto);
        model.addAttribute("accountTypes", kg.attractor.job_search_java_25.dto.AccountType.values());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("dto") RegistrationRequestDto dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.registration(dto);
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("registration.error", ex.getMessage());
            return "auth/register";
        }

        return "redirect:/auth/login?registered";
    }

}
