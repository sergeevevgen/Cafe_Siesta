package client.mvc;

import client.data.model.dto.ClientDto;
import client.data.model.dto.ProductDto;
import client.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable Long id, Model model) {
        model.addAttribute("client",
                userService.findClient(id));
        return "profile";
    }
    @GetMapping("/profile/edit/{id}")
    public String editProfile(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("clientId", id);
        model.addAttribute("clientDto", userService.findClient(id));
        return "profile-edit";
    }

    @PostMapping("/profile/save/{id}")
    public String saveProfile(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid ClientDto clientDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/profile/edit/{id}";
        }
        userService.updateData(id, clientDto);
        return "redirect:/client/profile/{id}";
    }
}
