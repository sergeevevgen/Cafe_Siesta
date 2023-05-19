package client.mvc;

import client.data.model.dto.ComboCartDto;
import client.data.model.dto.ComboDto;
import client.data.model.entity.Combo;
import client.data.model.entity.User;
import client.service.ComboService;
import client.service.ProductService;
import client.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;
    private static String getUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }
    public ComboMvcController(ComboService comboService, ProductService productService, UserService userService) {
        this.comboService = comboService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllCombos(Model model) {
        model.addAttribute("combos",
                comboService.findAllCombosDto());
        return "combos";
    }

    @GetMapping("/{id}")
    public String getCombo(@PathVariable Long id, Model model) {
        User user = userService.findByLogin(getUserName());
        Combo combo = comboService.findComboEntity(id);
        ComboCartDto dto = new ComboCartDto(combo);
        if (comboService.isComboInCart(user.getUser_id(), id)){
            dto.setIsInCart(1);
        }
        else {
            dto.setIsInCart(0);
        }
        model.addAttribute("combo", dto);
        model.addAttribute("products", productService.findProducts(new ComboDto(combo).getProducts()));
        return "combo";
    }
}
