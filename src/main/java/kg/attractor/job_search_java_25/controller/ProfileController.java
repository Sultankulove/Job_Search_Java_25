package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.service.ProfileService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping
    public String showProfile(Model model, Authentication auth) {
//        MyProfileDto dto = new MyProfileDto();
        MyProfileDto myProfileDto = profileService.getMyProfile(userService.findUserIdByEmail(auth.getName()));
        if (auth != null) {
            myProfileDto.setEmail(auth.getName());
        }


//        Long authId =


        model.addAttribute("user", myProfileDto);

//        model.addAttribute("user", dto);
        return "profile";
    }

    @PostMapping("avatar")
    public String uploadAvatar(
            @RequestPart("avatar") MultipartFile avatar,
            Principal principal
    ) {
        Long authId = userService.findUserIdByEmail(principal.getName());
        AvatarDto avatarDto = new AvatarDto(avatar, authId);


        log.info("POST /api/profile/avatar — загрузка аватара пользователем");
        profileService.addAvatar(avatarDto);

        return "redirect:/profile/edit";
    }



    @GetMapping("/edit")
    public String editProfile(Model model) {
        model.addAttribute("dto", new EditProfileDto());
        model.addAttribute("user", new UserProfileDto());
        return "edit_profile";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("dto") @Valid EditProfileDto dto,
                                BindingResult br,
                                Model model,
                                Principal principal) {
        if (br.hasErrors()) {
            model.addAttribute("errors", br.getAllErrors());
            System.out.println("errors: " + br.getAllErrors());
            return "edit_profile";
        }
        Long authId = userService.findUserIdByEmail(principal.getName());

        profileService.editProfile(dto, authId);

        return "redirect:/profile";
    }
}
