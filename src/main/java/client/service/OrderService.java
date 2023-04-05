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
import java.util.stream.Collectors;

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

    // Поиск всех заказов у клиента
    @Transactional(readOnly = true)
    public List<Order> findAllClientOrders(Long clientId) {
        List<Order> orderList = new ArrayList<>(Collections.emptyList());
        for (var order: repository.findAll()) {
            if(order.getClient().getId().equals(clientId)){
                orderList.add(order);
            }
        }
        return orderList;
    }

    // Отмена заказа у клиента
    @Transactional
    public Order cancelOrder(Long orderId) {
        Optional<Order> order = repository.findById(orderId);
        if (order.isPresent()) {
            order.get().setStatus(Order_Status.Rejected);
            repository.save(order.get());
        }
        return order.orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    //Изменение заказа по полям
    @Transactional
    public Order updateOrderProducts(Long id,
                             Map<Long, Long> products) {
//        if (!StringUtils.hasText(title) || price == null || price <= 0 ||  client_id == null || client_id < 0) {
//            throw new IllegalArgumentException("Order fields are null or empty");
//        }

        final Order current = findOrder(id);
        if (current == null) {
            throw new OrderNotFoundException(id);
        }

//        current.setTitle(title);
//        current.setPrice(price);
//        current.setStatus(status);

        if (products != null && !products.isEmpty()) {
            //Номера продуктов, которые сейчас используются
            Map<Long, Long> items = new HashMap<>();

            for(var i : current.getItems()) {
                if (items.containsKey(i.getProduct().getId())) {
                    items.put(i.getProduct().getId(), items.get(i.getProduct().getId()) + i.getCount());
                }
                else
                    items.put(i.getProduct().getId(), i.getCount());
            }
            //еще подумать над удалением !, возможно ниче не работает
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
        }

//        if (combos != null && !combos.isEmpty()) {
//            //Номера продуктов, которые сейчас используются
//            Map<Long, Long> items = new HashMap<>();
//
//            for(var i : current.getCombo_items()) {
//                if (items.containsKey(i.getCombo().getId())) {
//                    items.put(i.getCombo().getId(), items.get(i.getCombo().getId()) + i.getCount());
//                }
//                else
//                    items.put(i.getCombo().getId(), i.getCount());
//            }
//
//            for(var i : combos.entrySet()) {
//                if (!items.containsKey(i.getKey())) {
//                    Combo combo = comboService.findCombo(i.getKey());
//                    Combo_Order item = new Combo_Order();
//                    item.setCount(i.getValue());
//
//                    item.setCombo(combo);
//                    item.setOrder(current);
//
//                    combo_orderRepository.save(item);
//                }
//                else if (items.containsKey(i.getKey()) && !Objects.equals(items.get(i.getKey()), i.getValue())) {
//                    Combo_Order item = combo_orderRepository.getById(i.getKey());
//                    item.setCount(i.getValue());
//                    combo_orderRepository.save(item);
//                }
//            }
//        }

        validatorUtil.validate(current);
        return repository.save(current);
    }

    @Transactional
    public Order deleteOrderProduct(Long id ) {
        return null;
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
