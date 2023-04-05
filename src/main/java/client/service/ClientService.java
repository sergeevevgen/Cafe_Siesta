package client.service;

import client.data.model.entity.Client;
import client.data.repository.ClientRepository;
import client.service.exception.ClientNotFoundException;
import client.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;

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
}
