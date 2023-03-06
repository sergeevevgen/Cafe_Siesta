package client.rest;

import client.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    private final UserService loginService;

    public LoginController(UserService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/a")
    public String index() {
        return "<h1>sadasda</h1>";
    }

    @GetMapping("/b")
    public String sad() {
        return "<h2>sadasda</h2>";
    }

    @GetMapping("/c")
    public String cad() {
        return "<h1>asd</h1>";
    }
}
