package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dao.UserDao;
import kg.attractor.job_search_java_25.dto.RegistrationRequestDto;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/profile")
public class AuthController {
    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<RegistrationRequestDto> register(@RequestBody RegistrationRequestDto rrd) {
        // Регистрация: Имя! Email! NumberPhone! Avatar(if not default)
        userService.registration(rrd);
        return new ResponseEntity<>(rrd, HttpStatus.OK);
    }


}
