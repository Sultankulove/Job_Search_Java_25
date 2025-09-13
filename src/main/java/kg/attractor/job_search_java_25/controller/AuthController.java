package kg.attractor.job_search_java_25.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.userDtos.AccountType;
import kg.attractor.job_search_java_25.dto.userDtos.RegistrationRequestDto;
import kg.attractor.job_search_java_25.exceptions.types.ConflictException;
import kg.attractor.job_search_java_25.exceptions.types.UserNotFoundException;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;

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

        UserDetails userDetails = userService.loadUserByUsername(dto.getEmail());

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authToken);

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

        if (attributes instanceof ServletRequestAttributes servletRequestAttributes) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
            );
        }

        return "redirect:/";
    }

    @GetMapping("forgot-password")
    public String showForgotPasswordForm() {
        return "auth/forgot_password";
    }

    @PostMapping("forgot-password")
    public String processForgotPasswordForm(HttpServletRequest request,
                                            Model model) {
        try {
            userService.makeResetPasswordLink(request);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check it.");
        } catch (UserNotFoundException | UnsupportedEncodingException e) {
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException e) {
            model.addAttribute("error", "Error while sending email.");
        }
        return "auth/forgot_password";
    }

    @GetMapping("reset-password")
    public String resetPassword(@RequestParam(name = "token") String token, Model model) {
        try {
            userService.getByResetPasswordToken(token);
            model.addAttribute("token", token);
        } catch (UserNotFoundException e) {
            model.addAttribute("error", "Invalid token.");
        }
        return "auth/reset_password";
    }

    @PostMapping("reset-password")
    public String proccesResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("password");
        try {
            User user = userService.getByResetPasswordToken(token);
            userService.updatePassword(user, newPassword);
            model.addAttribute("message", "You have successfully changed your password.");
        } catch (UserNotFoundException e) {
            model.addAttribute("error", "Invalid token.");
        }
        return "message";

    }
}
