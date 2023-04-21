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
    //вроде закончен

    private final CategoryRepository repository;

    private final ValidatorUtil validatorUtil;

    public CategoryService(CategoryRepository repository, ValidatorUtil validatorUtil) {
        this.repository = repository;
        this.validatorUtil = validatorUtil;
    }

    //Создание категории через поля
    @Transactional
    public Category addCategory(String name, String description, String image_url) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Category fields are null or empty");
        }
        if (findByName(name) != null) {
            throw new ValidationException(String.format("Category '%s' is already exist", name));
        }
        final Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setImage_url(image_url);
        validatorUtil.validate(category);
        return repository.save(category);
    }

    //Создание категории через Dto
    @Transactional
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return new CategoryDto(addCategory(categoryDto.getName(), categoryDto.getDescription(), categoryDto.getImage_url()));
    }

    //Поиск категории в репозитории
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        final Optional<Category> category = repository.findById(id);
        return category.orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Category findByName(String name) {
        return repository.findOneByNameIgnoreCase(name);
    }

    //Поиск всех записей в репозитории
    @Transactional(readOnly = true)
    public List<CategoryDto> findAllCategories() {
        return repository.findAll().stream().map(CategoryDto::new).toList();
    }

    //Изменение категории по полям
    @Transactional
    public Category updateCategory(Long id, String name, String description, String image_url) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(description) || id == null || id < 0) {
            throw new IllegalArgumentException("Category fields are null or empty");
        }
        final Category current = findById(id);
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
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        return new CategoryDto(updateCategory(id, categoryDto.getName(), categoryDto.getDescription(), categoryDto.getImage_url()));
    }

    @Transactional
    public Category deleteCategory(Long id) {
        Category current = findById(id);
        repository.delete(current);
        return current;
    }

    public void deleteAllCategories() throws InCategoryFoundProductsException {
        List<Category> categories = repository.findAll();
        for (var c : categories) {
            if (c.getProducts().size() > 0) {
                throw new InCategoryFoundProductsException("Category with id [%s] has relational products");
            }
        }
        repository.deleteAll();
    }
}
