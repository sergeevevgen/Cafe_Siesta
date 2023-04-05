package client.service;

import client.data.model.dto.MessageDto;
import client.data.model.dto.OrderDto;
import client.data.model.entity.*;
import client.data.model.enums.Order_Status;
import client.data.repository.Combo_OrderRepository;
import client.data.repository.OrderRepository;
import client.data.repository.Order_ItemRepository;
import client.service.exception.MessageNotFoundException;
import client.service.exception.OrderNotFoundException;
import client.util.validation.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
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

    private final ValidatorUtil validatorUtil;

    public OrderService(OrderRepository repository, Order_ItemRepository order_itemRepository,
                        Combo_OrderRepository combo_orderRepository, ClientService clientService,
                        ProductService productService, ComboService comboService, ValidatorUtil validatorUtil) {
        this.repository = repository;
        this.order_itemRepository = order_itemRepository;
        this.combo_orderRepository = combo_orderRepository;
        this.clientService = clientService;
        this.productService = productService;
        this.comboService = comboService;
        this.validatorUtil = validatorUtil;
    }

    //Создание заказа через поля
    @Transactional
    public Order addOrder(String title, Double price, Long client_id, Map<Long, Long> products, Map<Long, Long> combos) {
        if (!StringUtils.hasText(title) || price == null || price <= 0 ||  client_id == null || client_id < 0) {
            throw new IllegalArgumentException("Order fields are null or empty");
        }
        final Order order = new Order();
        order.setTitle(title);
        order.setPrice(price);
        order.setClient(clientService.findById(client_id));
        order.setStatus(Order_Status.Is_cart);

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
                Combo combo = comboService.findCombo(c.getKey());
                Combo_Order item = new Combo_Order();
                item.setCount(c.getValue());

                item.setOrder(order);
                item.setCombo(combo);

                combo_orderRepository.save(item);
            }
        }

        validatorUtil.validate(order);
        return repository.save(order);
    }

    //Создание заказа через Dto
    @Transactional
    public OrderDto addOrder(OrderDto orderDto) {
        return new OrderDto(addOrder(orderDto.getTitle(), orderDto.getPrice(), orderDto.getClient_id(),
                orderDto.getProducts(), orderDto.getCombos()));
    }

    //Поиск заказа в репозитории
    @Transactional(readOnly = true)
    public Order findOrder(Long id) {
        final Optional<Order> order = repository.findById(id);
        return order.orElseThrow(() -> new OrderNotFoundException(id));
    }

    //Поиск всех записей в репозитории
    @Transactional(readOnly = true)
    public List<Order> findAllOrders() {
        return repository.findAll();
    }

    //Изменение заказа по полям
    @Transactional
    public Order updateOrder(Long id, String title, Double price, Order_Status status, Long client_id,
                             Map<Long, Long> products, Map<Long, Long> combos) {
        if (!StringUtils.hasText(title) || price == null || price <= 0 ||  client_id == null || client_id < 0) {
            throw new IllegalArgumentException("Order fields are null or empty");
        }

        final Order current = findOrder(id);
        if (current == null) {
            throw new OrderNotFoundException(id);
        }

        current.setTitle(title);
        current.setPrice(price);
        current.setStatus(status);

        if (!Objects.equals(current.getClient().getId(), client_id)) {
            current.setClient(clientService.findById(client_id));
        }

        if (products != null && !products.isEmpty()) {
            //Номера продуктов, которые сейчас используются
            List<Long> items = current.getItems().stream().map(Order_Item::getProduct).toList()
                    .stream().map(Product::getId).toList();

            var presProducts = products.keySet();
            //List<Order_Item> items1 = order_itemRepository.findByOrderId(current.getId());

        }

//        if (p != null) {
//            Product product = productService.findProduct(p.getFirst());
//            Order_Item order_item = new Order_Item();
//            order_item.setCount(p.getSecond());
//
//            order_item.setProduct(product);
//            order_item.setOrder(current);
//
//            order_itemRepository.save(order_item);
//        }
//
//        if (c != null) {
//            Combo combo = comboService.findCombo(c.getFirst());
//            Combo_Order combo_order = new Combo_Order();
//            combo_order.setCount(c.getSecond());
//
//            combo_order.setCombo(combo);
//            combo_order.setOrder(current);
//
//            combo_orderRepository.save(combo_order);
//        }

        validatorUtil.validate(current);
        return repository.save(current);
    }

    @Transactional
    public Order deleteOrderProduct(Long id, ) {

    }

    //Изменение категории по полям через Dto
    @Transactional
    public OrderDto updateOrder(OrderDto orderDto) {
        return new OrderDto(updateOrder(orderDto.getId(), orderDto.getTitle(), orderDto.getPrice(), orderDto.getStatus(),
                orderDto.getClient_id(), orderDto.getProducts(), orderDto.getCombos()));
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
