package client.rest_mobile;

import client.data.model.dto.ComboDto;
import client.data.model.entity.Combo;
import client.service.ComboService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/combos")
public class ComboController {
    private final ComboService comboService;

    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    @GetMapping("/getAll")
    public List<Combo> getAllCombos() {
        return comboService.findAllCombos();
    }

    @GetMapping("/getOne/{combo_id}")
    public ComboDto getOneCombo(@PathVariable Long id) {
        return comboService.findCombo(id);
    }

    @PostMapping("/addOne")
    public ComboDto createOne(@RequestBody ComboDto dto) {
        return comboService.addCombo(dto);
    }
}
