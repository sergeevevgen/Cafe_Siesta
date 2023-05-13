package client.mvc;

import client.data.model.dto.UserDto;
import client.data.model.entity.User;
import client.data.model.enums.UserRole;
import client.service.UserService;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.ValidationException;

@Controller
@RequestMapping(UserSignUpMvcController.SIGNUP_URL)
public class UserSignUpMvcController {
    public static final String SIGNUP_URL = "/signup";

    private final UserService userService;

    public UserSignUpMvcController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showSignupForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute @Valid UserDto userDto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "signup";
        }
        try {
            userDto.setRole(UserRole.CLIENT);
            final User user = userService.createUser(userDto);
            return "redirect:/login?created=" + user.getLogin();
        } catch (ValidationException e) {
            model.addAttribute("errors", e.getMessage());
            return "signup";
        }
    }
}

