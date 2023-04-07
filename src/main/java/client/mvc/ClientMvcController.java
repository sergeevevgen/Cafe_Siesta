package client.mvc;

import client.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login_html")
public class ClientMvcController {
    private final UserService loginService;

    public ClientMvcController(UserService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/a")
    public String index() {
        return "<h1>sadasda</h1><button>Button</button>";
    }
}
