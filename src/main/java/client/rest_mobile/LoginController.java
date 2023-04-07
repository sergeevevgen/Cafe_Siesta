package client.rest_mobile;

import client.configuration.WebConfiguration;
import client.data.model.dto.ClientDto;
import client.data.model.dto.DeliveryManDto;
import client.service.ClientService;
import client.service.DeliveryManService;
import client.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/login")
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
    public ClientDto authClient(@RequestBody ClientDto clientDto) {
        return clientService.authorize(clientDto);
    }

    @PostMapping("/authDeliveryMan")
    public DeliveryManDto authDeliveryMan(@RequestBody DeliveryManDto deliveryManDto) {
        return new DeliveryManDto(deliveryManService.authorize(deliveryManDto));
    }

    @PostMapping("/registerClient")
    public ClientDto registerClient(@RequestBody ClientDto clientDto) {
        return clientService.register(clientDto);
    }

    @PostMapping("/registerDeliveryMan")
    public DeliveryManDto registerDeliveryMan(@RequestBody DeliveryManDto deliveryManDto) {
        return new DeliveryManDto(deliveryManService.register(deliveryManDto));
    }
}
