package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.RegistrationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/profile")
public class AuthController {

    @PostMapping("register")
    public String register(@RequestBody RegistrationRequestDto rrd) {
        // Регистрация: Имя! Email! NumberPhone! Avatar(if not default)
        return "OK";
    }


}
