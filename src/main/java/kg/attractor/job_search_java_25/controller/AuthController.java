package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.RegistrationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AuthController {

    @PostMapping("register")
    public String register(@RequestBody RegistrationRequestDto RRD) {
        // Регистрация: Имя! Email! NumberPhone! Avatar(if not default)
        return "OK";
    }
}
