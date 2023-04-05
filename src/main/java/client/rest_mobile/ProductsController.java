package client.rest_mobile;

import client.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
