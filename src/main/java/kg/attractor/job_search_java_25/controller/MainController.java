package kg.attractor.job_search_java_25.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping
    public String index(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("authorities", authentication.getAuthorities());
        }
        model.addAttribute("message", "Hello World");
    return "index";
    }

}
