package client.rest_html;

import client.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login_html")
public class HtmlLoginController {
    private final UserService loginService;

    public HtmlLoginController(UserService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/a")
    public String index() {
        return "<h1>sadasda</h1><button>Button</button>";
    }
}
