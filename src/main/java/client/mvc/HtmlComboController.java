package client.mvc;

import client.service.ComboService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/combos_html")
public class HtmlComboController {
    private final ComboService comboService;

    public HtmlComboController(ComboService comboService) {
        this.comboService = comboService;
    }
}
