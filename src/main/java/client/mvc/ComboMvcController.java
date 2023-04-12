package client.mvc;

import client.service.ComboService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/combo")
public class ComboMvcController {
    private final ComboService comboService;

    public ComboMvcController(ComboService comboService) {
        this.comboService = comboService;
    }
}
