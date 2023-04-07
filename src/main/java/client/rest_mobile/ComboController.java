package client.rest_mobile;

import client.configuration.WebConfiguration;
import client.data.model.dto.ComboDto;
import client.data.model.entity.Combo;
import client.service.ComboService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/combo")
public class ComboController {
    private final ComboService comboService;

    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    @GetMapping("/getAll")
    public List<ComboDto> getAllCombos() {
        return comboService.findAllCombos().stream().map(ComboDto::new).toList();
    }

    @GetMapping("/getOne/{id}")
    public ComboDto getOneCombo(@PathVariable Long id) {
        return comboService.findCombo(id);
    }

    @PostMapping("/addOne")
    public ComboDto createOne(@RequestBody ComboDto dto) {
        return comboService.addCombo(dto);
    }

    @PostMapping("/updateOne")
    public ComboDto updateOne(@RequestBody ComboDto dto) {
        return comboService.updateCombo(dto);
    }
}
