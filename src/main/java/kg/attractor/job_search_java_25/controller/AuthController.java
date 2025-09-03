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
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/chooseRole")
    public String chooseRoleForRegistration() {
        return "auth/chooseRole";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }


    @GetMapping("/register")
    public String registerForm(Model model,
                               @RequestParam(required = false) String role) {

        RegistrationRequestDto dto = new RegistrationRequestDto();

        if ("applicant".equalsIgnoreCase(role)) {
            dto.setAccountType(AccountType.APPLICANT);
        } else if ("employer".equalsIgnoreCase(role)) {
            dto.setAccountType(AccountType.EMPLOYER);
        } else {
            return "auth/chooseRole";
        }

        model.addAttribute("dto", dto);
        model.addAttribute("role", role);
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("dto") RegistrationRequestDto dto,
                           BindingResult bindingResult,
                           @RequestParam(required = false) String role,
                           Model model) {

        model.addAttribute("role", role);
        if (dto.getAccountType() == null && role != null) {
            if ("applicant".equalsIgnoreCase(role)) {
                dto.setAccountType(AccountType.APPLICANT);
            } else if ("employer".equalsIgnoreCase(role)) {
                dto.setAccountType(AccountType.EMPLOYER);
            }
        }

        if (dto.getAccountType() == null) {
            bindingResult.rejectValue("accountType", "invalid", "Не удалось определить тип аккаунта.");
        }
        if (bindingResult.hasErrors()) {
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
