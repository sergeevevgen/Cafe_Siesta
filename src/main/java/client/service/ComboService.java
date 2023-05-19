package client.service;

import client.data.model.dto.ComboDto;
import client.data.model.dto.ProductDto;
import client.data.model.entity.Combo;
import client.data.model.entity.Order_Item;
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

import java.util.*;

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

    @Transactional(readOnly = true)
    public List<ComboDto> findAllCombosDto() {
        return findAllCombos()
                .stream()
                .map(ComboDto::new)
                .toList();
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

        if (product_ids != null && !product_ids.isEmpty()) {
            List<Long> products_prev = current.getProducts().stream().map(Product::getId).toList();
            Set<Long> products = new HashSet<>(products_prev);
            //Получили общие элементы, которые не надо удалять
            products.retainAll(product_ids); //Работает верно - оставил общие, которые не надо удалять в products
            //В products_ids остались только те, которые надо добавить


            //Удалили не нужные - работает верно
            for (var i : products_prev) {
                if (!products.contains(i)) {
                    current.removeProduct(i);
                }
            }

            product_ids.removeAll(products);
            //Добавили нужные - в продукт айдис должны остаться только новые, которые надо добавить, а сейчас тут все
            for (var i : product_ids) {
                current.setProduct(productService.findProduct(i));
            }

        }
        else {
            for (var p : current.getProducts().stream().map(Product::getId).toList()) {
                current.removeProduct(p);
            }
        }

        validatorUtil.validate(current);
        return repository.save(current);
    }

    //Изменение категории по полям через Dto
    @Transactional
    public ComboDto updateCombo(Long id, ComboDto comboDto) {
        return new ComboDto(updateCombo(id, comboDto.getName(), comboDto.getDescription(),
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

    @Transactional(readOnly = true)
    public List<ComboDto> findCombos(List<Long> ids) {
        return repository.findAllById(ids)
                .stream()
                .map(ComboDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Long> findCombosByClientCart(Long clientId) {
        return repository.findCombosByClient(clientId)
                .stream()
                .map(Combo::getId)
                .toList();
    }

    @Transactional(readOnly = true)
    public Boolean isComboInCart(Long clientId, Long comboId) {
        return findCombosByClientCart(clientId)
                .contains(comboId);
    }
}
