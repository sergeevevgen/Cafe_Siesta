package client.mvc;

import client.data.model.dto.ComboDto;
import client.service.ComboService;
import client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/combos")
public class ComboMvcController {
    private final ComboService comboService;
    private final ProductService productService;
    public ComboMvcController(ComboService comboService, ProductService productService) {
        this.comboService = comboService;
        this.productService = productService;
    }

    @GetMapping
    public String getAllCombos(Model model) {
        model.addAttribute("combos",
                comboService.findAllCombos());
        return "combos";
    }

    @GetMapping("/{id}")
    public String getCombo(@PathVariable Long id, Model model) {
        ComboDto dto = comboService.findCombo(id);
        model.addAttribute("combo", dto);
        model.addAttribute("products", productService.findProducts(dto.getProducts()));
        return "combo";
    }
}
