package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.AvatarDto;
import kg.attractor.job_search_java_25.dto.EmployerDto;
import kg.attractor.job_search_java_25.dto.UserDto;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;



    @PostMapping("create")
    public ResponseEntity<UserDto> createUser() {
        userService.createUser();
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.editUser(id, userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("id")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }


    /// ///////////
    @GetMapping("avatar")
    public ResponseEntity<?> getImage(@RequestParam(name = "filename") String filename) {
        return userService.getImageById(filename);
    }

    @PostMapping("avatar")
    public HttpStatus addImage(AvatarDto avatarDto) {
        userService.addImage(avatarDto);
        return HttpStatus.CREATED;
    }
}
