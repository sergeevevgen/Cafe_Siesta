package client.service;

import client.data.model.dto.ComboDto;
import client.data.model.entity.Combo;
import client.data.model.entity.Product;
import client.data.repository.ComboRepository;
import client.service.exception.ComboNotFoundException;
import client.service.exception.InComboFoundCombo_OrderException;
import client.util.validation.ValidationException;
import client.util.validation.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ComboService {
    //вроде закончен
    private final Logger log = LoggerFactory.getLogger(ComboService.class);

    private final ComboRepository repository;
    private final ProductService productService;

    private final ValidatorUtil validatorUtil;

    public ComboService(ComboRepository repository, ValidatorUtil validatorUtil, ProductService productService) {
        this.repository = repository;
        this.validatorUtil = validatorUtil;
        this.productService = productService;
    }

    //Создание комбо через поля
    @Transactional
    public Combo addCombo(String name, String description, String image_url, Double sale, Double price, List<Long> product_ids) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(description) || price == null || price < 0) {
            throw new IllegalArgumentException("Combo fields are null or empty");
        }
        if (findComboByName(name) != null) {
            throw new ValidationException(String.format("Combo '%s' is already exist", name));
        }
        final Combo combo = new Combo();
        combo.setName(name);
        combo.setDescription(description);
        combo.setImage_url(image_url);
        combo.setSale(sale);
        combo.setPrice(price);

        for (var i : product_ids) {
            combo.setProduct(productService.findProduct(i));
        }

        validatorUtil.validate(combo);
        return repository.save(combo);
    }

    //Создание комбо через Dto
    @Transactional
    public ComboDto addCombo(ComboDto comboDto) {
        return new ComboDto(
                addCombo(
                        comboDto.getName(),
                        comboDto.getDescription(),
                        comboDto.getImage_url(),
                        comboDto.getSale(),
                        comboDto.getPrice(),
                        comboDto.getProducts()
                ));
    }

    //Поиск комбо в репозитории
    @Transactional(readOnly = true)
    public ComboDto findCombo(Long id) {
        return new ComboDto(findComboEntity(id));
    }

    @Transactional(readOnly = true)
    public Combo findComboEntity(Long id) {
        final Optional<Combo> combo = repository.findById(id);
        return combo.orElseThrow(() -> new ComboNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Combo findComboByName(String name) {
        return repository.findOneByNameIgnoreCase(name);
    }

    //Поиск всех записей в репозитории
    @Transactional(readOnly = true)
    public List<Combo> findAllCombos() {
        return repository.findAll();
    }

    //Изменение категории по полям
    @Transactional
    public Combo updateCombo(Long id, String name, String description, String image_url, Double sale, Double price, List<Long> product_ids) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(description) || price == null || price < 0) {
            throw new IllegalArgumentException("Combo fields are null or empty");
        }
        final var current = findComboEntity(id);
        if (current == null) {
            throw new ComboNotFoundException(id);
        }

        if (!current.getName().equals(name) && findComboByName(name) != null) {
            throw new ValidationException(String.format("Combo '%s' is already exist", name));
        }

        current.setName(name);
        current.setDescription(description);
        current.setImage_url(image_url);
        current.setPrice(price);
        current.setSale(sale);

        //Вычленение прошлых продуктов и замена новыми с помощью множества
        List<Long> products_prev = current.getProducts().stream().map(Product::getId).toList();
        Set<Long> products = new HashSet<>(products_prev);
        products.addAll(product_ids);
        for (var i : products) {
            current.setProduct(productService.findProduct(i));
        }

        validatorUtil.validate(current);
        return repository.save(current);
    }

    //Изменение категории по полям через Dto
    @Transactional
    public ComboDto updateCombo(ComboDto comboDto) {
        return new ComboDto(updateCombo(comboDto.getId(), comboDto.getName(), comboDto.getDescription(),
                comboDto.getImage_url(), comboDto.getSale(), comboDto.getPrice(), comboDto.getProducts()));
    }

    @Transactional
    public Combo deleteCombo(Long id) {
        Combo current = findComboEntity(id);
        repository.delete(current);
        return current;
    }

    public void deleteAllCombos() throws InComboFoundCombo_OrderException {
        List<Combo> combos = findAllCombos();
        for (var c : combos) {
            if (c.getItems().size() > 0) {
                throw new InComboFoundCombo_OrderException("Combo with id [%s] has relationship with order");
            }
        }
        repository.deleteAll();
    }
}
