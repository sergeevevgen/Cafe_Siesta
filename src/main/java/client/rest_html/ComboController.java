package client.rest_html;

import client.service.ComboService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/combos_html")
public class ComboController {
    private final ComboService comboService;

    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }
}
