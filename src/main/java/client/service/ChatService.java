package client.service;

import client.data.model.dto.CategoryDto;
import client.data.model.dto.MessageDto;
import client.data.model.entity.Category;
import client.data.model.entity.Chat;
import client.data.model.entity.Message;
import client.data.repository.ChatRepository;
import client.data.repository.MessageRepository;
import client.service.exception.CategoryNotFoundException;
import client.service.exception.ChatNotFoundException;
import client.service.exception.InCategoryFoundProductsException;
import client.util.validation.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    //Не закончен

    private final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final ChatRepository repository;

    private final ValidatorUtil validatorUtil;

    public ChatService(ChatRepository repository, ValidatorUtil validatorUtil) {
        this.repository = repository;
        this.validatorUtil = validatorUtil;
    }

    // Создание категории через дто
    @Transactional
    public Chat createChat(String title) {
        if (!StringUtils.hasText(title)
        ) {
            throw new IllegalArgumentException("Chat fields are null or empty");
        }

        final Chat chat = new Chat();
        chat.setTitle(title);
        validatorUtil.validate(chat);
        return repository.save(chat);
    }

    @Transactional(readOnly = true)
    public Chat findChat(Long id) {
        final Optional<Chat> chat = repository.findById(id);
        return chat.orElseThrow(() -> new ChatNotFoundException(id));
    }

    // Наподобие такого, ищем все чаты у клиента
    // Все очень просто
    @Transactional(readOnly = true)
    public List<Chat> findClientChats(Long clientId) {
        return repository.findByClientId(clientId);
    }

    @Transactional(readOnly = true)
    public List<Chat> findDeliverymanChats(Long deliveryManId) {
        return repository.findByDeliveryManId(deliveryManId);
    }

    @Transactional
    public Chat deleteChat(Long id) {
        Chat current = findChat(id);
        repository.delete(current);
        return current;
    }
}
