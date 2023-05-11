package client.service;

import client.data.model.dto.OrderDto;
import client.data.model.entity.*;
import client.data.model.enums.Order_Status;
import client.data.repository.Combo_OrderRepository;
import client.data.repository.OrderRepository;
import client.data.repository.Order_ItemRepository;
import client.service.exception.ClientNotFoundException;
import client.service.exception.DeliveryManNotFoundException;
import client.service.exception.OrderNotFoundException;
import client.service.exception.WrongOrderStatusException;
import client.util.validation.ValidatorUtil;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class OrderService {
    //не начат
    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository repository;
    private final Order_ItemRepository order_itemRepository;
    private final Combo_OrderRepository combo_orderRepository;
    private final ClientService clientService;
    private final ProductService productService;
    private final ComboService comboService;
    private final ChatService chatService;
    private final DeliveryManService deliveryManService;

    private final ValidatorUtil validatorUtil;

    public OrderService(OrderRepository repository, Order_ItemRepository order_itemRepository,
                        Combo_OrderRepository combo_orderRepository, ClientService clientService,
                        ProductService productService, ComboService comboService, ChatService chatService, DeliveryManService deliveryManService, ValidatorUtil validatorUtil) {
        this.repository = repository;
        this.order_itemRepository = order_itemRepository;
        this.combo_orderRepository = combo_orderRepository;
        this.clientService = clientService;
        this.productService = productService;
        this.comboService = comboService;
        this.chatService = chatService;
        this.deliveryManService = deliveryManService;
        this.validatorUtil = validatorUtil;
    }

    //Создание заказа через поля
    @Transactional
    public Order addOrder(Double price, Long client_id, Map<Long, Long> products, Map<Long, Long> combos) {
        if (price == null || price < 0 ||  client_id == null || client_id < 0) {
            throw new IllegalArgumentException("Order fields are null or empty");
        }
        final Order order = new Order();
        order.setPrice(price);
        order.setTitle("Заказ #" + UUID.randomUUID().toString().substring(0, 8));
        final Client client = clientService.findById(client_id);
        if (client == null) {
            throw new ClientNotFoundException(client_id);
        }
        order.setClient(client);
        order.setStatus(Order_Status.Is_cart);
        if (client.getStreet() != null) {
            order.setStreet(client.getStreet());
        }
        if (client.getHouse() != null) {
            order.setHouse(client.getHouse());
        }
        if (client.getFlat() != null) {
            order.setFlat(client.getFlat());
        }
        if (client.getEntrance() != null) {
            order.setEntrance(client.getEntrance());
        }

        if (products != null && !products.isEmpty()) {
            for (var p : products.entrySet()) {
                Product product = productService.findProduct(p.getKey());
                Order_Item order_item = new Order_Item();
                order_item.setCount(p.getValue());

                order_item.setProduct(product);
                order_item.setOrder(order);

                order_itemRepository.save(order_item);
            }
        }

        if (combos != null && !combos.isEmpty()) {
            for (var c : combos.entrySet()) {
                Combo combo = comboService.findComboEntity(c.getKey());
                Combo_Order item = new Combo_Order();
                item.setCount(c.getValue());

                item.setOrder(order);
                item.setCombo(combo);

                combo_orderRepository.save(item);
            }
        }
        //Создание чата при создании заказа
        Chat chat = chatService.createChat(order.getTitle());
        order.setChat(chat);

        validatorUtil.validate(order);

        return repository.save(order);
    }

    @Transactional
    public Order setAddress(Long id,
                            String street,
                            String house,
                            String flat,
                            String entrance) {
        if (id == null || id <= 0 || !StringUtils.hasText(street) || !StringUtils.hasText(house)) {
            throw new IllegalArgumentException("Order address fields are null or empty");
        }
        final Order current = findOrder(id);
        if (current == null) {
            throw new OrderNotFoundException(id);
        }
        current.setStreet(street);
        current.setHouse(house);
        if (StringUtils.hasText(flat))
        {
            current.setFlat(flat);
        }
        if (StringUtils.hasText(entrance))
        {
            current.setEntrance(entrance);
        }
        validatorUtil.validate(current);
        return repository.save(current);
    }

    @Transactional
    public OrderDto setAddress(OrderDto orderDto) {
        return new OrderDto(setAddress(orderDto.getId(), orderDto.getStreet(), orderDto.getHouse(), orderDto.getFlat(), orderDto.getEntrance()));
    }

    //Создание заказа через Dto
    @Transactional
    public OrderDto addOrder(OrderDto orderDto) {
        return new OrderDto(addOrder(orderDto.getPrice(), orderDto.getClient_id(),
                orderDto.getProducts(), orderDto.getCombos()));
    }

    //Поиск заказа в репозитории
    @Transactional(readOnly = true)
    public Order findOrder(Long id) {
        final Optional<Order> order = repository.findById(id);
        return order.orElseThrow(() -> new OrderNotFoundException(id));
    }

    //Поиск заказа в репозитории
    @Transactional(readOnly = true)
    public OrderDto findOrder(OrderDto orderDto) {
        return new OrderDto(findOrder(orderDto.getId()));
    }

    //Поиск всех записей в репозитории
    @Transactional(readOnly = true)
    public List<OrderDto> findAllOrders() {
        return repository.findAll().stream().map(OrderDto::new).toList();
    }


    // Поиск всех заказов у клиента
    @Transactional(readOnly = true)
    public List<OrderDto> findAllClientOrders(Long clientId) {
        return findAllClientOrdersEntities(clientId).stream().map(OrderDto::new).toList();
    }

    // Поиск всех заказов у клиента по Dto НЕ НУЖЕН
    @Transactional(readOnly = true)
    public List<Order> findAllClientOrdersEntities(Long clientId) {
        return repository.findByClientId(clientId);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findAllDeliveryOrdersEntities(Long deliveryId) {
        return repository.findByDeliveryId(deliveryId).stream().map(OrderDto::new).toList();
    }

    // Изменение статуса заказа у клиента
    @Transactional
    public Order changeOrderStatus(Long orderId, Order_Status status) {
        Optional<Order> order = repository.findById(orderId);
        if (order.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }
        Order current = order.get();
        switch (status) {
            case Accepted: {
                if (current.getStatus() == Order_Status.Is_cart) {
                    current.setStatus(status);
                    addOrder(0.0, current.getClient().getId(), null, null);
                }
                else {
                    throw new WrongOrderStatusException(current.getId(), current.getStatus());
                }
                break;
            }
            case In_process:
                break;
            case Rejected: {
                if (current.getStatus() == Order_Status.Accepted) {
                    current.setStatus(status);
                }
                else {
                    throw new WrongOrderStatusException(current.getId(), current.getStatus());
                }
                break;
            }
            case Done: {
                if (current.getStatus() == Order_Status.In_process
                        || current.getStatus() == Order_Status.On_the_way) {
                    current.setStatus(status);
                }
                else {
                    throw new WrongOrderStatusException(current.getId(), current.getStatus());
                }
                break;
            }
            case On_the_way: {
                if (current.getStatus() == Order_Status.Done) {
                    current.setStatus(status);
                }
                else {
                    throw new WrongOrderStatusException(current.getId(), current.getStatus());
                }
                break;
            }
            case Finish: {
                if (current.getStatus() == Order_Status.On_the_way) {
                    current.setStatus(status);
                }
                else {
                    throw new WrongOrderStatusException(current.getId(), current.getStatus());
                }
                break;
            }
        }

        return repository.save(current);
    }

    // Изменение статуса заказа у клиента через Dto
    @Transactional
    public OrderDto changeOrderStatus(OrderDto orderDto) {
        return new OrderDto(changeOrderStatus(orderDto.getId(), orderDto.getStatus()));
    }

    //Изменение заказа по полям
    @Transactional
    public Order updateOrderProducts(
            Long id,
            Map<Long, Long> products
    ) {

        final Order current = findOrder(id);
        if (current == null) {
            throw new OrderNotFoundException(id);
        }

        if (products != null && !products.isEmpty()) {
            //Номера продуктов, которые сейчас используются
            Map<Long, Long> items = new HashMap<>();

            //Тут существующие продукты из заказа с количеством
            for(var i : current.getItems()) {
                if (items.containsKey(i.getProduct().getId())) {
                    items.put(i.getProduct().getId(), items.get(i.getProduct().getId()) + i.getCount());
                }
                else
                    items.put(i.getProduct().getId(), i.getCount());
            }

            //Добавляем новые
            for(var i : products.entrySet()) {
                if (!items.containsKey(i.getKey())) {
                    Product product = productService.findProduct(i.getKey());
                    Order_Item order_item = new Order_Item();
                    order_item.setCount(i.getValue());

                    order_item.setProduct(product);
                    order_item.setOrder(current);

                    order_itemRepository.save(order_item);
                }
                else if (items.containsKey(i.getKey()) && !Objects.equals(items.get(i.getKey()), i.getValue())) {
                    Order_Item order_item = order_itemRepository.getById(i.getKey());
                    order_item.setCount(i.getValue());
                    order_itemRepository.save(order_item);
                }
            }
            //Удаляем старые
            for(var i : items.entrySet()) {
                if (!products.containsKey(i.getKey())) {
                    order_itemRepository.delete(order_itemRepository.getById(i.getKey()));
                }
            }
        }
        else {
            order_itemRepository.deleteAllById(current.getItems().stream().map(Order_Item::getId).toList());
        }

        validatorUtil.validate(current);
        return repository.save(current);
    }

    @Transactional
    public Order updateOrderCombos(
            Long id,
            Map<Long, Long> combos
    ) {
        final Order current = findOrder(id);
        if (current == null) {
            throw new OrderNotFoundException(id);
        }

        if (combos != null && !combos.isEmpty()) {
            //Номера комбо, которые сейчас используются
            Map<Long, Long> items = new HashMap<>();

            //Тут существующие комбо из заказа с количеством
            for(var i : current.getCombo_items()) {
                if (items.containsKey(i.getCombo().getId())) {
                    items.put(i.getCombo().getId(), items.get(i.getCombo().getId()) + i.getCount());
                }
                else
                    items.put(i.getCombo().getId(), i.getCount());
            }

            //Добавляем новые
            for(var i : combos.entrySet()) {
                if (!items.containsKey(i.getKey())) {
                    Combo combo = comboService.findComboEntity(i.getKey());
                    Combo_Order item = new Combo_Order();
                    item.setCount(i.getValue());

                    item.setCombo(combo);
                    item.setOrder(current);

                    combo_orderRepository.save(item);
                }
                else if (items.containsKey(i.getKey()) && !Objects.equals(items.get(i.getKey()), i.getValue())) {
                    Combo_Order item = combo_orderRepository.getById(i.getKey());
                    item.setCount(i.getValue());
                    combo_orderRepository.save(item);
                }
            }
            //Удаляем старые
            for(var i : items.entrySet()) {
                if (!combos.containsKey(i.getKey())) {
                    combo_orderRepository.delete(combo_orderRepository.getById(i.getKey()));
                }
            }
        }
        else {
            combo_orderRepository.deleteAllById(current.getCombo_items().stream().map(Combo_Order::getId).toList());
        }

        validatorUtil.validate(current);
        return repository.save(current);
    }

    @Transactional
    public Order updateOrderFields(
            Long id,
            Double price,
            String title
    ) {
        if (!StringUtils.hasText(title) || price == null || price < 0) {
            throw new IllegalArgumentException("Order fields are null or empty");
        }
        final Order order = findOrder(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }
        order.setTitle(title);
        order.setPrice(price);
        validatorUtil.validate(order);
        return repository.save(order);
    }

    @Transactional
    public OrderDto updateOrderFields(
            OrderDto orderDto
    ) {
        return new OrderDto(updateOrderFields(orderDto.getId(), orderDto.getPrice(), orderDto.getTitle()));
    }

    @Transactional
    public Order updateOrderDeliveryMan(
            Long id,
            Long deliveryman_id
    ) {
        if (id == null || id <= 0 || deliveryman_id == null || deliveryman_id <= 0) {
            throw new IllegalArgumentException("Order fields are null or empty");
        }
        final Order order = findOrder(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }
        final DeliveryMan deliveryMan = deliveryManService.findById(deliveryman_id);
        if (deliveryMan == null) {
            throw new DeliveryManNotFoundException(deliveryman_id);
        }
        order.setDeliveryMan(deliveryMan);
        return repository.save(order);
    }

    @Transactional
    public Order findClientCart(
            Long client_id
    ) {
        if (client_id == null || client_id <= 0) {
            throw new IllegalArgumentException("Client_id is null");
        }
        final Client client = clientService.findById(client_id);
        if (client == null) {
            throw new ClientNotFoundException(client_id);
        }
        return repository.findCartByClient(client_id);
    }

    @Transactional
    public OrderDto findClientCart(
            OrderDto orderDto
    ) {
        return new OrderDto(findClientCart(orderDto.getClient_id()));
    }

    @Transactional
    public OrderDto updateOrderDeliveryMan(
            OrderDto orderDto
    ) {
        return new OrderDto(updateOrderDeliveryMan(orderDto.getId(), orderDto.getDeliveryman_id()));
    }


    //Добавление к заказу комбо
    @Transactional
    public OrderDto updateOrderCombos(OrderDto orderDto) {
        return new OrderDto(updateOrderCombos(orderDto.getId(), orderDto.getCombos()));
    }

    //Добавление к заказу продуктов
    @Transactional
    public OrderDto updateOrderProducts(OrderDto orderDto) {
        return new OrderDto(updateOrderProducts(orderDto.getId(), orderDto.getProducts()));
    }

    @Transactional
    public Order deleteOrder(Long id) {
        Order current = findOrder(id);
        repository.delete(current);
        return current;
    }

    @Transactional
    public void deleteAllOrders() {
        repository.deleteAll();
    }
}
