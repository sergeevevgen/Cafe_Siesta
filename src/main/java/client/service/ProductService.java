package client.service;

import client.data.model.entity.Product;
import client.data.repository.ProductRepository;
import client.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ValidatorUtil validatorUtil;
    public ProductService(ProductRepository productRepository, ValidatorUtil validatorUtil) {
        this.productRepository = productRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProducts() { return productRepository.findAll(); }
}
