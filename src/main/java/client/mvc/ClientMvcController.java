package client.mvc;

import client.service.ClientService;
import client.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientMvcController {
    private final ClientService userService;

    public ClientMvcController(ClientService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("client",
                userService.findById(14L));
        return "profile";
    }
}
