package client.service;

import client.data.model.dto.CategoryDto;
import client.data.model.entity.Category;
import client.data.model.entity.Product;
import client.data.repository.CategoryRepository;
import client.data.repository.ProductRepository;
import client.service.exception.CategoryNotFoundException;
import client.service.exception.InCategoryFoundProductsException;
import client.util.validation.ValidationException;
import client.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    //не закончен
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ValidatorUtil validatorUtil;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ValidatorUtil validatorUtil) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
    }

    //Поиск всех записей в репозитории
    @Transactional(readOnly = true)
    public List<Product> findAllProducts() { return productRepository.findAll(); }

/*    //Создание категории через поля
    @Transactional
    public Product addProduct(String name, String description, String image_url, Long weight, Double price, String category) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(description) || !StringUtils.hasText(category) ||
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
        final Category category1 = categoryRepository.findOneByNameIgnoreCase(category);
        product.setCategory(category1);
        validatorUtil.validate(category);
        return productRepository.save(product);
    }*/

    /*//Создание категории через Dto
    @Transactional
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return new CategoryDto(addCategory(categoryDto.getName(), categoryDto.getDescription()));
    }

    //Поиск категории в репозитории
    @Transactional(readOnly = true)
    public Category findCategory(Long id) {
        final Optional<Category> category = repository.findById(id);
        return category.orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Product findProductByName(String name) {
        return productRepository.findOneByNameIgnoreCase(name);
    }

    //Изменение категории по полям
    @Transactional
    public Category updateCategory(Long id, String name, String description, String image_url) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Category fields are null or empty");
        }
        final Category current = findCategory(id);
        if (current == null) {
            throw new CategoryNotFoundException(id);
        }
        current.setName(name);
        current.setDescription(description);
        current.setImage_url(image_url);
        validatorUtil.validate(current);
        return repository.save(current);
    }

    //Изменение категории по полям через Dto
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        return new CategoryDto(updateCategory(categoryDto.getId(), categoryDto.getName(), categoryDto.getDescription(), categoryDto.getImage_url()));
    }

    @Transactional
    public Category deleteCategory(Long id) {
        Category current = findCategory(id);
        repository.delete(current);
        return current;
    }

    public void deleteAllCategories() throws InCategoryFoundProductsException {
        List<Category> categories = findAllCategories();
        for (var c : categories) {
            if (c.getProducts().size() > 0) {
                throw new InCategoryFoundProductsException("Category with id [%s] has relational products");
            }
        }
        repository.deleteAll();
    }*/
}
