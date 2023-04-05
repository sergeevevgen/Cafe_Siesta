package client.service;

import client.data.model.dto.ClientDto;
import client.data.model.entity.Client;
import client.data.repository.ClientRepository;
import client.service.exception.ClientNotFoundException;
import client.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ValidatorUtil validatorUtil;
    public ClientService(ClientRepository clientRepository, ValidatorUtil validatorUtil){
        this.clientRepository = clientRepository;
        this.validatorUtil = validatorUtil;
    }

    public Client findById(Long id) {
        final Optional<Client> client = clientRepository.findById(id);
        return client.orElseThrow(() -> new ClientNotFoundException(id));
    }

    public Client authorize(ClientDto dto) {
        // реализовать авторизацию по дто
        // у клиента хотел бы видеть поле пароль
        final List<Client> clients = clientRepository.findAll();
        Optional<Client> clientFounded = Optional.empty();
        for (var client: clients) {
            if(client.getLogin().equals(dto.getLogin())){
                clientFounded = Optional.of(client);
            }
        }
        return clientFounded.orElseThrow(() -> new ClientNotFoundException(dto.getId()));
    }

    public Client register(ClientDto dto) {
        // реализовать регистрацию по дто
        final Optional<Client> client = clientRepository.findById(dto.getId());
        return client.orElseThrow(() -> new ClientNotFoundException(dto.getId()));
    }

}
