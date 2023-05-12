package client.service;

import client.data.model.dto.ClientDto;
import client.data.model.dto.UserDto;
import client.data.model.entity.Client;
import client.data.model.entity.User;
import client.data.model.enums.UserRole;
import client.data.repository.UserRepository;
import client.service.exception.ClientNotFoundException;
import client.service.exception.UserNotFoundException;
import client.util.validation.ValidatorUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {
    //не закончен
    private final UserRepository userRepository;
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private final ValidatorUtil validatorUtil;

    public UserService(UserRepository userRepository,
                       ClientService clientService, PasswordEncoder passwordEncoder, ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
        this.validatorUtil = validatorUtil;
    }

    public User findByLogin(String login) {
        return userRepository.findOneByLoginIgnoreCase(login);
    }

    public User createUser(UserDto dto) {
        return createUser(dto.getLogin(), dto.getPassword(), dto.getName(), dto.getSurname(), dto.getPhone_number(), dto.getRole());
    }

    public User createUser(String login, String password, String name, String surname, String phone_number, UserRole role) {
        if (!StringUtils.hasText(login) || !StringUtils.hasText(password) || password.length() < 8 || !StringUtils.hasText(name)
                || !StringUtils.hasText(surname) || !StringUtils.hasText(phone_number) || role == null) {
            throw new IllegalArgumentException("User fields are null or empty");
        }

        if (findByLogin(login) != null) {
            throw new ValidationException(String.format("User '%s' already exists", login));
        }
        Client client = clientService.register(name, surname, password, login, phone_number,
                null, null, null, null);
        final User user = new User();
        user.setUser_id(client.getId());
        user.setLogin(client.getLogin());
        user.setName(client.getName());
        user.setPassword(passwordEncoder.encode(client.getPassword()));
        user.setRole(role);
        user.setSurname(surname);
        validatorUtil.validate(user);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User userEntity = findByLogin(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(
                userEntity.getLogin(), userEntity.getPassword(), Collections.singleton(userEntity.getRole()));
    }

    public  User updateUser(ClientDto dto) {
        return updateUser(dto.getLogin(), dto.getName(), dto.getSurname(), dto.getStreet(), dto.getEntrance(),
                dto.getFlat(), dto.getHouse(), dto.getPhone_number());
    }

    public User updateUser(String login, String name, String surname, String street,
                             String entrance, String flat, String house, String phone_number) {
        if (!StringUtils.hasText(login) || !StringUtils.hasText(name) || !StringUtils.hasText(surname)) {
            throw new IllegalArgumentException("User fields are null or empty");
        }
        final User user = findByLogin(login);
        if (user == null) {
            throw new UserNotFoundException(login);
        }
        user.setName(name);
        user.setSurname(surname);
        ClientDto clientDto = new ClientDto();
        clientDto.setName(name);
        clientDto.setSurname(surname);
        clientDto.setPhone_number(phone_number);
        if (StringUtils.hasText(street)) {
            clientDto.setStreet(street);
        }
        if (StringUtils.hasText(entrance)) {
            clientDto.setEntrance(entrance);
        }
        if (StringUtils.hasText(flat)) {
            clientDto.setFlat(flat);
        }
        if (StringUtils.hasText(house)) {
            clientDto.setHouse(house);
        }
        clientService.updateData(user.getUser_id(), clientDto);

        validatorUtil.validate(user);
        return userRepository.save(user);
    }
}
