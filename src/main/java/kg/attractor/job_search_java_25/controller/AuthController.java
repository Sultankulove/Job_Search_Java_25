package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.AccountType;
import kg.attractor.job_search_java_25.dto.RegistrationRequestDto;
import kg.attractor.job_search_java_25.exceptions.types.ConflictException;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    public String registerForm(Model model, @ModelAttribute("dto") RegistrationRequestDto dto) {

        model.addAttribute("dto", new RegistrationRequestDto());

        model.addAttribute("accountTypes", List.of(AccountType.APPLICANT, AccountType.EMPLOYER));
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("dto") RegistrationRequestDto dto,
            BindingResult bindingResult,
            Model model) {

        if (dto.getAccountType() == null ||
                (dto.getAccountType() != AccountType.APPLICANT &&
                        dto.getAccountType() != AccountType.EMPLOYER)) {
            bindingResult.rejectValue("accountType", "invalid", "Выберите тип аккаунта");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("accountTypes",
                    java.util.List.of(AccountType.APPLICANT, AccountType.EMPLOYER));
            return "auth/register";
        }

        try {
            userService.registration(dto);
        } catch (ConflictException ex) {
            String key = ex.getMessage();
            if ("email".equalsIgnoreCase(key)) {
                bindingResult.rejectValue("email", "duplicate", "Email уже используется");
            } else if ("phoneNumber".equalsIgnoreCase(key)) {
                bindingResult.rejectValue("phoneNumber", "duplicate", "Телефон уже используется");
            } else {
                bindingResult.reject("registration.conflict", "Данные уже используются");
            }
            return "auth/register";
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("registration.error", ex.getMessage());
            return "auth/register";
        }

        return "redirect:/auth/login?registered";
    }
}
