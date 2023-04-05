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
    private final OrderService orderService;

    private final ValidatorUtil validatorUtil;

    public ChatService(ChatRepository repository, OrderService orderService, ValidatorUtil validatorUtil) {
        this.repository = repository;
        this.validatorUtil = validatorUtil;
        this.orderService = orderService;
    }

    // Создание категории через дто
    @Transactional
    public Chat createMessage(MessageDto messageDto) {
        if (!StringUtils.hasText(messageDto.getText())
                || messageDto.getSender_id() == null
                || messageDto.getSender_id() <= 0
                || messageDto.getChat_id() == null
                || messageDto.getChat_id() <= 0
                || messageDto.getTime() == null
        ) {
            throw new IllegalArgumentException("Message fields are null or empty");
        }
        return null;
    }

    //Создание категории через Dto
//    @Transactional
//    public CategoryDto addCategory(CategoryDto categoryDto) {
//        return new CategoryDto(addCategory(categoryDto.getName(), categoryDto.getDescription()));
//    }

    //Поиск категории в репозитории
    @Transactional(readOnly = true)
    public Chat findChat(Long id) {
        final Optional<Chat> chat = repository.findById(id);
        return chat.orElseThrow(() -> new ChatNotFoundException(id));
    }

    // Наподобие такого, ищем все чаты у клиента
    // Все очень просто
    @Transactional(readOnly = true)
    public List<Chat> findUserChats(Long clientId) {
        final List<Chat> chats = repository.findAll();
        List<Chat> chatsFound = Collections.emptyList();
        for (var chat: chats) {
            if(chat.getOrder().getClient().getId().equals(clientId)){
                chats.add(chat);
            }
        }
        return chatsFound;
    }

    //Поиск всех записей в репозитории
//    @Transactional(readOnly = true)
//    public List<Category> findAllCategories() {
//        return repository.findAll();
//    }
//
//    //Изменение категории по полям
//    @Transactional
//    public Category updateCategory(Long id, String name, String description, String image_url) {
//        if (!StringUtils.hasText(name) || !StringUtils.hasText(description)) {
//            throw new IllegalArgumentException("Category fields are null or empty");
//        }
//
//        final Category current = findCategory(id);
//        current.setName(name);
//        current.setDescription(description);
//        current.setImage_url(image_url);
//        validatorUtil.validate(current);
//        return repository.save(current);
//    }
//
//    //Изменение категории по полям через Dto
//    @Transactional
//    public CategoryDto updateCategory(CategoryDto categoryDto) {
//        return new CategoryDto(updateCategory(categoryDto.getId(), categoryDto.getName(), categoryDto.getDescription(), categoryDto.getImage_url()));
//    }
//
//    @Transactional
//    public Category deleteCategory(Long id) {
//        Category current = findCategory(id);
//        repository.delete(current);
//        return current;
//    }
//
//    public void deleteAllCategories() throws InCategoryFoundProductsException {
//        List<Category> categories = findAllCategories();
//        for (var c : categories) {
//            if (c.getProducts().size() > 0) {
//                throw new InCategoryFoundProductsException("Category with id [%s] has relational products");
//            }
//        }
//        repository.deleteAll();
//    }
}
