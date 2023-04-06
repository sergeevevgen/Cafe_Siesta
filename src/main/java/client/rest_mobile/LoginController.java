package client.rest_mobile;

import client.data.model.dto.ClientDto;
import client.data.model.dto.DeliveryManDto;
import client.service.ClientService;
import client.service.DeliveryManService;
import client.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService loginService;
    private final ClientService clientService;
    private final DeliveryManService deliveryManService;

    public LoginController(UserService loginService, ClientService clientService, DeliveryManService deliveryManService) {
        this.loginService = loginService;
        this.clientService = clientService;
        this.deliveryManService = deliveryManService;
    }

    @PostMapping("/authClient")
    public ClientDto authClient(ClientDto clientDto) {
        // нет сервиса на авторизацию пользователя
        // return authClient(ClientDto dto): ClientDto;
        return clientService.authorize(clientDto);
    }

    @PostMapping("/authClient")
    public String authDeliveryMan(DeliveryManDto deliveryManDto) {
        // нет сервиса на авторизацию пользователя
        // return authDeliveryMan(DeliveryManDto dto): DeliveryManDto;
        return deliveryManService.authorize(deliveryManDto).toString();
    }

    @PostMapping("/registerClient")
    public String registerClient(ClientDto clientDto) {
        // нет сервиса на авторизацию пользователя
        // return registerClient(ClientDto dto): ClientDto;
        return clientService.register(clientDto).toString();
    }

    @PostMapping("/registerClient")
    public String registerDeliveryMan(DeliveryManDto deliveryManDto) {
        // нет сервиса на авторизацию пользователя
        // return registerDeliveryMan(DeliveryManDto dto): DeliveryManDto;
        return "";
    }
}
