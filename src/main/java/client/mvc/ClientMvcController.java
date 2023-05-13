package client.mvc;

import client.data.model.dto.ClientDto;
import client.data.model.entity.User;
import client.service.ClientService;
import client.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ClientMvcController {
    private final ClientService clientService;
    private final UserService userService;
    public ClientMvcController(ClientService clientService, UserService userService) {
        this.clientService = clientService;
        this.userService = userService;
    }

    @GetMapping
    public String getProfile(Model model) {

//        model.addAttribute("clientDto",
//                clientService.findClient(user.getUser_id()));
        return "profile";
    }

    @GetMapping("/edit")
    public String editProfile(Model model, Principal principal) {
        User user = (User) principal;
        model.addAttribute("clientDto", clientService.findClient(user.getUser_id()));
        return "profile-edit";
    }

    @PostMapping("/save")
    public String saveProfile(@ModelAttribute @Valid ClientDto clientDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/edit";
        }
        userService.updateUser(clientDto);
        return "redirect:/profile";
    }
}
