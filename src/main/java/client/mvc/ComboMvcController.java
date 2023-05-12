package client.mvc;

import client.service.ComboService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/combos")
public class ComboMvcController {
    private final ComboService comboService;

    public ComboMvcController(ComboService comboService) {
        this.comboService = comboService;
    }

    @GetMapping
    public String GetAllCombos(Model model) {
        model.addAttribute("combos",
                comboService.findAllCombos());
        return "combos";
    }

    @GetMapping("/{id}")
    public String getCombo(@PathVariable Long id, Model model) {
        model.addAttribute("combo",
                comboService.findCombo(id));
        return "combo";
    }
}
