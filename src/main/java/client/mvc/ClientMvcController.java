package client.mvc;

import client.data.model.dto.ClientDto;
import client.data.model.entity.User;
import client.service.ClientService;
import client.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static String getUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }
    public ClientMvcController(ClientService clientService, UserService userService) {
        this.clientService = clientService;
        this.userService = userService;
    }

    @GetMapping
    public String getProfile(Model model) {
        User user = userService.findByLogin(getUserName());
        model.addAttribute("clientDto",
                clientService.findClient(user.getUser_id()));
        return "profile";
    }

    @GetMapping("/edit")
    public String editProfile(Model model) {
        User user = userService.findByLogin(getUserName());
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
        clientDto.setLogin(getUserName());
        userService.updateUser(clientDto);
        return "redirect:/profile";
    }
}
