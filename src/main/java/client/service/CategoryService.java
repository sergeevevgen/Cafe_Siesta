package client.service;

import client.data.model.dto.CategoryDto;
import client.data.model.entity.Category;
import client.data.repository.CategoryRepository;
import client.service.exception.CategoryNotFoundException;
import client.service.exception.InCategoryFoundProductsException;
import client.util.validation.ValidationException;
import client.util.validation.ValidatorUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository repository;

    private final ValidatorUtil validatorUtil;

    public CategoryService(CategoryRepository repository, ValidatorUtil validatorUtil) {
        this.repository = repository;
        this.validatorUtil = validatorUtil;
    }

    //Создание категории через поля
    @Transactional
    public Category addCategory(String name, String description) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Category fields are null or empty");
        }
        if (findCategoryByName(name) != null) {
            throw new ValidationException(String.format("Category '%s' is already exist", name));
        }
        final Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setImage_url(null);
        validatorUtil.validate(category);
        return repository.save(category);
    }

    //Создание категории через Dto
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
    public Category findCategoryByName(String name) {
        return repository.findOneByNameIgnoreCase(name);
    }

    //Поиск всех записей в репозитории
    @Transactional(readOnly = true)
    public List<Category> findAllCategories() {
        return repository.findAll();
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
    }
}
