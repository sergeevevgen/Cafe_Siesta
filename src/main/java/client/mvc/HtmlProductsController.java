package client.mvc;

import client.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products_html")
public class HtmlProductsController {
    private final ProductService productService;

    public HtmlProductsController(ProductService productService) {
        this.productService = productService;
    }
}
