package client.service;

import client.data.model.dto.ProductDto;
import client.data.model.entity.Category;
import client.data.model.entity.Product;
import client.data.repository.ProductRepository;
import client.service.exception.ProductNotFoundException;
import client.util.validation.ValidationException;
import client.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    //вроде закончен
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ValidatorUtil validatorUtil;

    public ProductService(ProductRepository productRepository, CategoryService categoryService,
                          ValidatorUtil validatorUtil) {
        this.productRepository = productRepository;
        this.validatorUtil = validatorUtil;
        this.categoryService = categoryService;
    }

    //Поиск всех записей в репозитории
    @Transactional(readOnly = true)
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll().stream().map(ProductDto::new).toList();
    }

    //Создание продукта через поля
    @Transactional
    public Product addProduct(String name,
                              String description,
                              String image_url,
                              Long weight,
                              Double price,
                              Long category_id) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(description) || category_id == null || category_id <= 0 ||
                weight == null || weight < 0 || price == null || price < 0) {
            throw new IllegalArgumentException("Product fields are null or empty");
        }
        if (findProductByName(name) != null) {
            throw new ValidationException(String.format("Product '%s' is already exist", name));
        }
        final Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setImage_url(image_url);
        product.setWeight(weight);
        product.setPrice(price);
        final Category category = categoryService.findById(category_id);
        product.setCategory(category);
        validatorUtil.validate(product);
        return productRepository.save(product);
    }

    //Создание продукта через Dto
    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        return new ProductDto(
                addProduct(
                        productDto.getName(),
                        productDto.getDescription(),
                        productDto.getImage_url(),
                        productDto.getWeight(),
                        productDto.getPrice(),
                        productDto.getCategory_id()
                )
        );
    }

    //Поиск продукта в репозитории
    @Transactional(readOnly = true)
    public Product findProduct(Long id) {
        final Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ProductNotFoundException(id));
    }

    //Поиск продукта в репозитории
    @Transactional(readOnly = true)
    public ProductDto findProduct(ProductDto productDto) {
        return new ProductDto(findProduct(productDto.getId()));
    }

    @Transactional(readOnly = true)
    public Product findProductByName(String name) {
        return productRepository.findOneByNameIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findProductsByCategory(String name) { return productRepository.findProductsByCategory(name)
            .stream()
            .map(ProductDto::new)
            .toList(); }

    //Изменение продукта по полям
    @Transactional
    public Product updateProduct(Long id, String name,
                                 String description,
                                 String image_url,
                                 Long weight,
                                 Double price,
                                 Long category_id) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(description) || id == null || id <= 0 || weight == null
                || weight < 0 || price == null || price < 0 || category_id == null || category_id <= 0) {
            throw new IllegalArgumentException("Product fields are null or empty");
        }
        final Product product = findProduct(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        if (!product.getName().equals(name) && findProductByName(name) != null) {
            throw new IllegalArgumentException(String.format("Product with name [%s] is existed", name));
        }
        product.setName(name);
        product.setDescription(description);
        product.setImage_url(image_url);
        product.setWeight(weight);
        product.setPrice(price);

        final Category category = categoryService.findById(category_id);
        if (product.getCategory().getId().equals(category_id)) {
            product.getCategory().updateProduct(product);
        } else {
            product.getCategory().removeProduct(id);
            product.setCategory(category);
        }

        validatorUtil.validate(product);
        return productRepository.save(product);
    }

    //Изменение продукта по полям через Dto
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        return new ProductDto(updateProduct(id, productDto.getName(), productDto.getDescription(),
                productDto.getImage_url(), productDto.getWeight(), productDto.getPrice(), productDto.getCategory_id()));
    }

    @Transactional
    public Product deleteProduct(Long id) {
        Product current = findProduct(id);
        productRepository.delete(current);
        return current;
    }

    @Transactional
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findProducts(List<Long> ids) {
        return productRepository.findAllById(ids)
                .stream()
                .map(ProductDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Long> findProductsByClientCart(Long clientId) {
        return productRepository.findProductsByClient(clientId)
                .stream()
                .map(Product::getId)
                .toList();
    }

    @Transactional(readOnly = true)
    public Boolean isProductInCart(Long clientId, Long productId) {
        return findProductsByClientCart(clientId)
                .contains(productId);
    }
}
