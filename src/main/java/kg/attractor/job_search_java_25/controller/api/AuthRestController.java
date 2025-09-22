package kg.attractor.job_search_java_25.controller.api;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.userDtos.RegistrationRequestDto;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/profile")
public class AuthRestController {
    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<RegistrationRequestDto> register(@RequestBody @Valid RegistrationRequestDto rrd) {
        log.info("POST /api/profile/register - попытка регистрации email={}", rrd.getEmail());
        userService.registration(rrd);
        return ResponseEntity.ok(rrd);
    }

}
