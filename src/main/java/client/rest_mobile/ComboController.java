package client.rest_mobile;

import client.data.model.dto.CategoryDto;
import client.data.model.dto.ComboDto;
import client.service.ComboService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addOne")
    public String createOne(@RequestBody ComboDto dto) {
        return comboService.addCombo(dto).toString();
    }
}
