package client.service;

import client.data.model.dto.CategoryDto;
import client.data.model.entity.Category;
import client.data.repository.CategoryRepository;
import client.util.validation.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
public class CategoryService {
    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository repository;

    private final ValidatorUtil validatorUtil;

    public CategoryService(CategoryRepository repository, ValidatorUtil validatorUtil) {
        this.repository = repository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Category addCategory(String name, String description) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Enter data");
        }
        final Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        validatorUtil.validate(category);
        return repository.save(category);
    }

    @Transactional
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return new CategoryDto();
    }
}
