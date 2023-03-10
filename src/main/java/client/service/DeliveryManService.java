package client.service;

import client.data.model.dto.CategoryDto;
import client.data.model.dto.DeliveryManDto;
import client.data.model.entity.Category;
import client.data.model.entity.DeliveryMan;
import client.data.model.enums.DeliveryMan_Status;
import client.data.repository.CategoryRepository;
import client.data.repository.DeliveryManRepository;
import client.service.exception.CategoryNotFoundException;
import client.service.exception.DeliveryManNotFoundException;
import client.service.exception.InCategoryFoundProductsException;
import client.util.validation.ValidationException;
import client.util.validation.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public class DeliveryManService {
    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final DeliveryManRepository repository;

    private final ValidatorUtil validatorUtil;

    public DeliveryManService(DeliveryManRepository repository, ValidatorUtil validatorUtil) {
        this.repository = repository;
        this.validatorUtil = validatorUtil;
    }

    //Создание доставщика через поля
    @Transactional
    public DeliveryMan createDeliveryMan(String name, String surname, String login, String password) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(surname) || !StringUtils.hasText(login) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("DeliveryMan fields are null or empty");
        }
        if (findByLogin(login) != null) {
            throw new ValidationException(String.format("DeliveryMan with login '%s' is already exist", login));
        }
        final DeliveryMan deliveryMan = new DeliveryMan();
        deliveryMan.setName(name);
        deliveryMan.setSurname(surname);
        deliveryMan.setLogin(login);
        deliveryMan.setPassword(password);
        deliveryMan.setImage_url(null);
        deliveryMan.setStatus(DeliveryMan_Status.Offline);
        validatorUtil.validate(deliveryMan);
        return repository.save(deliveryMan);
    }

    //Создание доставщика через Dto
    @Transactional
    public DeliveryManDto creteDeliveryMan(DeliveryManDto deliveryManDto) {
        return new DeliveryManDto(createDeliveryMan(deliveryManDto.getName(), deliveryManDto.getSurname(),
                deliveryManDto.getLogin(), deliveryManDto.getPassword()));
    }

    //Поиск доставщика по логину
    @Transactional
    public DeliveryMan findByLogin(String login) {
        return repository.findOneByLoginIgnoreCase(login);
    }

    //Поиск доставщика по Id
    @Transactional
    public DeliveryMan findById(Long id) {
        final Optional<DeliveryMan> deliveryMan = repository.findById(id);
        return deliveryMan.orElseThrow(() -> new DeliveryManNotFoundException(id));
    }

    //Поиск всех записей в репозитории
    @Transactional(readOnly = true)
    public List<DeliveryMan> findAllDeliveryMan() {
        return repository.findAll();
    }

    //Изменение доставщика по полям
    @Transactional
    public DeliveryMan updateDeliveryMan(Long id, String name, String surname, String password) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(surname) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("DeliveryMan fields are null or empty");
        }
        final DeliveryMan current = findById(id);
        if (current == null) {
            throw new DeliveryManNotFoundException(id);
        }
        current.setName(name);
        current.setSurname(surname);
        current.setPassword(password);
        validatorUtil.validate(current);
        return repository.save(current);
    }

    //Изменение доставщика по полям через Dto
    @Transactional
    public DeliveryManDto updateDeliveryMan(DeliveryManDto deliveryManDto) {
        return new DeliveryManDto(updateDeliveryMan(deliveryManDto.getId(), deliveryManDto.getName(), deliveryManDto.getSurname(), deliveryManDto.getPassword()));
    }

    //Удаление доставщика
    @Transactional
    public DeliveryMan deleteDeliveryMan(Long id) {
        DeliveryMan current = findById(id);
        repository.delete(current);
        return current;
    }

    @Transactional
    public DeliveryMan deleteDeliveryMan(String login) {
        DeliveryMan current = findByLogin(login);
        repository.delete(current);
        return current;
    }
}
