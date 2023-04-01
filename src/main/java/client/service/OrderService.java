package client.service;

import client.data.model.dto.MessageDto;
import client.data.model.dto.OrderDto;
import client.data.model.entity.Chat;
import client.data.model.entity.Message;
import client.data.model.entity.Order;
import client.data.repository.MessageRepository;
import client.data.repository.OrderRepository;
import client.service.exception.MessageNotFoundException;
import client.util.validation.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    //не начат
    private final Logger log = LoggerFactory.getLogger(MessageService.class);

    private final OrderRepository repository;

    private final ValidatorUtil validatorUtil;

    public OrderService(OrderRepository repository, ValidatorUtil validatorUtil) {
        this.repository = repository;
        this.validatorUtil = validatorUtil;
    }

    //Создание категории через поля
    @Transactional
    public Message addMessage(String text, LocalDateTime time, Long sender_id, Long chat_id) {
        if (!StringUtils.hasText(text) || sender_id == null || sender_id <= 0 || chat_id == null || chat_id <= 0 || time == null) {
            throw new IllegalArgumentException("Message fields are null or empty");
        }
//        final Message message = new Message();
//        message.setText(text);
//        message.setTime(time);
//        message.setSender_id(sender_id);
//        final Chat chat = chatService.findChat(chat_id);
//        message.setChat(chat);
//        validatorUtil.validate(message);
//        return repository.save(message);
        return null;
    }

    //Создание категории через Dto
    @Transactional
    public MessageDto addMessage(MessageDto messageDto) {
        return new MessageDto(addMessage(messageDto.getText(), messageDto.getTime(), messageDto.getSender_id(), messageDto.getChat_id()));
    }

    //Поиск категории в репозитории
    @Transactional(readOnly = true)
    public Order findMessage(Long id) {
        final Optional<Order> message = repository.findById(id);
        return message.orElseThrow(() -> new MessageNotFoundException(id));
    }

    //Поиск всех записей в репозитории
    @Transactional(readOnly = true)
    public List<Order> findAllMessages() {
        return repository.findAll();
    }

    //Изменение категории по полям
    @Transactional
    public Order updateMessage(Long id, String text, LocalDateTime time, Long sender_id, Long chat_id) {
        if (!StringUtils.hasText(text) || sender_id == null || sender_id <= 0 || chat_id == null || chat_id <= 0 || time == null) {
            throw new IllegalArgumentException("Message fields are null or empty");
        }
        final Order current = findMessage(id);
        if (current == null) {
            throw new MessageNotFoundException(id);
        }
//        current.setText(text);
//        current.setTime(time);
//        current.setSender_id(sender_id);
//
//        if (current.getChat().getId().equals(chat_id)) {
//            current.getChat().updateMessage(current);
//        }
//        else {
//            current.getChat().removeMessage(id);
//            current.setChat(chatService.findChat(chat_id));
//        }

        validatorUtil.validate(current);
        return repository.save(current);
    }

    //Изменение категории по полям через Dto
    @Transactional
    public OrderDto updateMessage(MessageDto messageDto) {
        return new OrderDto(updateMessage(messageDto.getId(), messageDto.getText(), messageDto.getTime(), messageDto.getSender_id(), messageDto.getChat_id()));
    }

    @Transactional
    public Order deleteMessage(Long id) {
        Order current = findMessage(id);
        repository.delete(current);
        return current;
    }

    @Transactional
    public void deleteAllMessages() {
        repository.deleteAll();
    }

    @Transactional
    public void deleteAllByChat(Long chat_id) {
        ;
    }
}
