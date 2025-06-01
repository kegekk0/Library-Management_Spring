package pl.edu.pja.tpo_12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pja.tpo_12.dto.PublisherRegistrationDto;
import pl.edu.pja.tpo_12.dto.ReaderRegistrationDto;
import pl.edu.pja.tpo_12.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/reader")
    public String showReaderRegistrationForm(Model model) {
        model.addAttribute("reader", new ReaderRegistrationDto());
        model.addAttribute("pageTitle", "Register as Reader");
        return "register/reader";
    }

    @PostMapping("/reader")
    public String registerReader(@ModelAttribute("reader") ReaderRegistrationDto registrationDto) {
        userService.registerReader(registrationDto);
        return "redirect:/register/success";
    }

    @GetMapping("/publisher")
    public String showPublisherRegistrationForm(Model model) {
        model.addAttribute("publisher", new PublisherRegistrationDto());
        model.addAttribute("pageTitle", "Register as Publisher");
        return "register/publisher";
    }

    @PostMapping("/publisher")
    public String registerPublisher(@ModelAttribute("publisher") PublisherRegistrationDto registrationDto) {
        userService.registerPublisher(registrationDto);
        return "redirect:/register/success";
    }

    @GetMapping("/success")
    public String registrationSuccess(Model model) {
        model.addAttribute("pageTitle", "Registration Successful");
        return "register/success";
    }
}