package client.rest_mobile;

import client.service.ComboService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/combos")
public class ComboController {
    private final ComboService comboService;

    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    @GetMapping("/getAll")
    public String getAllCombos() {
        return comboService.findAllCombos().toString();
    }

    @GetMapping("/getOne/{combo_id}")
    public String getOneCombo(@PathVariable Long id) {
        return comboService.findCombo(id).toString();
    }
}
