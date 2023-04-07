package client.rest_mobile;

import client.configuration.WebConfiguration;
import client.data.model.dto.ProductDto;
import client.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/product")
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public List<ProductDto> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/getOne/{product_id}")
    public ProductDto getOneProduct(@RequestBody ProductDto productDto) {
        return productService.findProduct(productDto);
    }

    @PostMapping("/addOne")
    public ProductDto createOne(@RequestBody ProductDto dto) {
        return productService.addProduct(dto);
    }
}
