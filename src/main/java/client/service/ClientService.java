package client.service;

import client.data.model.dto.ClientDto;
import client.data.model.entity.Client;
import client.data.model.entity.Combo;
import client.data.repository.ClientRepository;
import client.service.exception.ClientNotFoundException;
import client.service.exception.ComboNotFoundException;
import client.util.validation.ValidationException;
import client.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\\\"(?:[\\\\x01-\\\\x08\\\\x0b\\\\x0c\\\\x0e-\\\\x1f\\\\x21\\\\x23-\\\\x5b\\\\x5d-\\\\x7f]|\\\\\\\\[\\\\x01-\\\\x09\\\\x0b\\\\x0c\\\\x0e-\\\\x7f])*\\\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\\\x01-\\\\x08\\\\x0b\\\\x0c\\\\x0e-\\\\x1f\\\\x21-\\\\x5a\\\\x53-\\\\x7f]|\\\\\\\\[\\\\x01-\\\\x09\\\\x0b\\\\x0c\\\\x0e-\\\\x7f])+)\\\\])";
    private final ValidatorUtil validatorUtil;
    public ClientService(ClientRepository clientRepository, ValidatorUtil validatorUtil){
        this.clientRepository = clientRepository;
        this.validatorUtil = validatorUtil;
    }

    public Client findById(Long id) {
        final Optional<Client> client = clientRepository.findById(id);
        return client.orElseThrow(() -> new ClientNotFoundException(id));
    }

    //Авторизация
    public Client authorize(String login, String password) {
        // реализовать авторизацию по дто
        // у клиента хотел бы видеть поле пароль
        Client client = clientRepository.findByLogin(login);
        if (client == null) {
            throw new ClientNotFoundException(login);
        }
        if (client.getPassword().equals(password)) {
            return client;
        }
        return null;
    }

    public ClientDto authorize(ClientDto clientDto) {
        return new ClientDto(authorize(clientDto.getLogin(), clientDto.getPassword()));
    }

    //регистрация
    public Client register(String name, String surname, String password, String login, String phone_number, String street,
                           String entrance, String flat, String house) {
//        if (!StringUtils.hasText(name) || !StringUtils.hasText(surname) || !StringUtils.hasText(password)
//                || !StringUtils.hasText(login) || !PatternMatchUtils.simpleMatch(pattern, login)) {
//            throw new IllegalArgumentException("Client fields are null or empty");
//        }
        if (!StringUtils.hasText(name) || !StringUtils.hasText(surname) || !StringUtils.hasText(password)
                || !StringUtils.hasText(login) || !StringUtils.hasText(phone_number)) {
            throw new IllegalArgumentException("Client fields are null or empty");
        }
        if (clientRepository.findByLogin(login) != null) {
            throw new ValidationException(String.format("Client '%s' is already exist", login));
        }
        final Client client = new Client();
        client.setLogin(login);
        client.setName(name);
        client.setSurname(surname);
        client.setPassword(password);
        client.setStreet(street);
        client.setFlat(flat);
        client.setHouse(house);
        client.setEntrance(entrance);
        client.setPhone_number(phone_number);
        validatorUtil.validate(client);
        return clientRepository.save(client);
    }

    public ClientDto register(ClientDto dto) {
        return new ClientDto(register(dto.getName(), dto.getSurname(), dto.getPassword(), dto.getLogin(), dto.getPhone_number(), dto.getStreet(), dto.getEntrance(), dto.getFlat(), dto.getHouse()));
    }

    @Transactional(readOnly = true)
    public Client findClientEntity(Long id) {
        final Optional<Client> client = clientRepository.findById(id);
        return client.orElseThrow(() -> new ClientNotFoundException(id));
    }

    public Client updateData(Long id, String name, String surname, String password, String login, String street,
                             String entrance, String flat, String house) {
        if (id == null || id < 0 || !StringUtils.hasText(name) || !StringUtils.hasText(surname) || !StringUtils.hasText(password)
                || !StringUtils.hasText(login)) {
            throw new IllegalArgumentException("Client fields are null or empty");
        }
        final Client client = findClientEntity(id);
        if (client == null) {
            throw new ClientNotFoundException(id);
        }
        if (!client.getLogin().equals(login) && clientRepository.findByLogin(login) != null) {
            throw new ValidationException(String.format("Client '%s' is already exist", login));
        }
        client.setLogin(login);
        client.setName(name);
        client.setSurname(surname);
        client.setPassword(password);
        client.setStreet(street);
        client.setFlat(flat);
        client.setHouse(house);
        client.setEntrance(entrance);
        validatorUtil.validate(client);
        return clientRepository.save(client);
    }

    public ClientDto updateData(Long id, ClientDto clientDto) {
        return new ClientDto(updateData(clientDto.getId(), clientDto.getName(), clientDto.getSurname(), clientDto.getPassword(),
                clientDto.getLogin(), clientDto.getStreet(), clientDto.getEntrance(), clientDto.getFlat(),
                clientDto.getHouse()));
    }

    public List<ClientDto> getClients() {
        return clientRepository.findAll().stream().map(ClientDto::new).toList();
    }
}
