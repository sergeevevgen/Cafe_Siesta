package client.mvc;

import client.data.model.dto.*;
import client.data.model.entity.User;
import client.data.model.enums.Order_Status;
import client.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminMvcController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ComboService comboService;
    private final OrderService orderService;
    private final DeliveryManService deliveryManService;

    public AdminMvcController(ProductService productService,
                              CategoryService categoryService,
                              ComboService comboService, OrderService orderService, DeliveryManService deliveryManService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.comboService = comboService;
        this.orderService = orderService;
        this.deliveryManService = deliveryManService;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable Long id, Model model) {
        OrderDto order = new OrderDto(orderService.findOrder(id));
        model.addAttribute("order", order);
        //Собираем продукты в корзине
        List<ProductDto> products = productService.findProducts(order.getProducts()
                .keySet()
                .stream()
                .toList());
        List<ProductCartDto> productsCart =  new ArrayList<>();
        for (int i = 0; i < order.getProducts().size(); ++i) {
            if (order.getProducts().containsKey(products.get(i).getId())) {
                ProductCartDto productCartDto = new ProductCartDto(products.get(i),
                        order.getProducts().get(products.get(i).getId()));
                productsCart.add(productCartDto);
            }
        }
        model.addAttribute("products", productsCart);

        //собираем комбо в корзине
        List<ComboDto> combos = comboService.findCombos(order.getCombos()
                .keySet()
                .stream()
                .toList());
        List<ComboCartDto> comboCartDtos =  new ArrayList<>();
        for (int i = 0; i < order.getCombos().size(); ++i) {
            if (order.getCombos().containsKey(combos.get(i).getId())) {
                ComboCartDto comboCartDto = new ComboCartDto(combos.get(i),
                        order.getCombos().get(combos.get(i).getId()));
                comboCartDtos.add(comboCartDto);
            }
        }
        model.addAttribute("combos", comboCartDtos);
        model.addAttribute("deliveryman", new DeliveryManDto(deliveryManService.findById(order.getDeliveryman_id())));
        return "order";
    }
    @GetMapping
    public String getMainPage(Model model) {
        model.addAttribute("orders", orderService.findOrders());

        return "orders-admin";
    }

    //Products
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        return "products";
    }

    @GetMapping(value = {"/products/edit", "/products/edit/{id}"})
    public String editProduct(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("productDto", new ProductDto());
        }
        else {
            model.addAttribute("productId", id);
            model.addAttribute("productDto", new ProductDto(productService.findProduct(id)));
        }
        model.addAttribute("categories", categoryService.findAllCategories());
        return "product-edit";
    }

    @PostMapping(value = {"/products/save", "/products/save/{id}"})
    public String saveProduct(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid ProductDto productDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "product-edit";
        }
        if (id == null || id <= 0) {
            productService.addProduct(productDto);
        } else {
            productService.updateProduct(id, productDto);
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }


    //Categories
    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories",
                categoryService.findAllCategories());
        return "categories";
    }

    @GetMapping(value = {"/categories/edit", "/categories/edit/{id}"})
    public String editCategory(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("categoryDto", new CategoryDto());
        }
        else {
            model.addAttribute("categoryId", id);
            model.addAttribute("categoryDto", new CategoryDto(categoryService.findById(id)));
        }
        return "category-edit";
    }

    @PostMapping(value = {"/categories/save", "/categories/save/{id}"})
    public String saveCategory(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid CategoryDto categoryDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "category-edit";
        }
        if (id == null || id <= 0) {
            categoryService.addCategory(categoryDto);
        } else {
            categoryService.updateCategory(id, categoryDto);
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/combos")
    public String getAllCombos(Model model) {
        model.addAttribute("combos",
                comboService.findAllCombos());
        return "combos-admin";
    }

    @GetMapping(value = {"/combos/edit", "/combos/edit/{id}"})
    public String editCombo(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("comboDto", new ComboDto());
        }
        else {
            model.addAttribute("comboId", id);
            model.addAttribute("comboDto", new ComboDto(comboService.findComboEntity(id)));
        }
        model.addAttribute("products", productService.findAllProducts());
        return "combo-edit";
    }

    @PostMapping(value = {"/combos/save", "/combos/save/{id}"})
    public String saveCombo(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid ComboDto comboDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "combo-edit";
        }
        if (id == null || id <= 0) {
            comboService.addCombo(comboDto);
        } else {
            comboService.updateCombo(id, comboDto);
        }
        return "redirect:/admin/combos";
    }

    @PostMapping("/combos/delete/{id}")
    public String deleteCombo(@PathVariable Long id) {
        comboService.deleteCombo(id);
        return "redirect:/admin/combos";
    }

    @GetMapping("/deliveryman")
    public String getAllDeliveryMen(Model model) {
        model.addAttribute("deliveryman",
                deliveryManService.findAllDeliveryMan());
        return "deliveryman-admin";
    }

    @GetMapping(value = {"/deliveryman/edit", "/deliveryman/edit/{id}"})
    public String editDeliveryMan(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("deliverymanDto", new DeliveryManDto());
        }
        else {
            model.addAttribute("deliverymanId", id);
            model.addAttribute("deliverymanDto", new DeliveryManDto(deliveryManService.findById(id)));
        }
        model.addAttribute("orders", orderService.findOrders());
        return "deliveryman-edit";
    }

    @PostMapping(value = {"/deliveryman/save", "/deliveryman/save/{id}"})
    public String saveDeliveryMan(@PathVariable(required = false) Long id,
                            @ModelAttribute @Valid DeliveryManDto deliveryManDto,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "deliveryman-edit";
        }
        if (id == null || id <= 0) {
            deliveryManService.register(deliveryManDto);
        } else {
            deliveryManService.updateDeliveryMan(id, deliveryManDto);
        }
        return "redirect:/admin/deliveryman";
    }

    @PostMapping("/deliverymen/delete/{id}")
    public String deleteDeliveryMan(@PathVariable Long id) {
        deliveryManService.deleteDeliveryMan(id);
        return "redirect:/admin/deliveryman";
    }

    @PostMapping("/cancelOrder/{id}")
    public String cancelOrder(@PathVariable Long id) {
        OrderDto order = new OrderDto(orderService.findOrder(id));
        orderService.changeOrderStatus(order.getId(), Order_Status.Rejected);
        return "redirect:/admin";
    }

    @PostMapping("/changeOrderStatus/{id}")
    public String changeOrderStatus(@PathVariable Long id) {
        OrderDto order = new OrderDto(orderService.findOrder(id));
        orderService.changeOrderStatus(order.getId());
        return "redirect:/admin";
    }
}
