package client.rest_mobile;

import client.data.model.dto.OrderDto;
import client.data.model.dto.ProductDto;
import client.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public String getAllProducts() {
        return productService.findAllProducts().toString();
    }

    @GetMapping("/getOne/{product_id}")
    public String getOneProduct(@PathVariable Long id) {
        return productService.findProduct(id).toString();
    }

    @PostMapping("/addOne")
    public String createOne(@RequestBody ProductDto dto) {
        return productService.addProduct(dto).toString();
    }
}
