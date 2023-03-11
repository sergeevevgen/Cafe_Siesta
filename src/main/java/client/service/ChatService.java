package client.service;

import client.data.model.dto.CategoryDto;
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
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final ChatRepository repository;

    private final ValidatorUtil validatorUtil;

    public ChatService(ChatRepository repository, ValidatorUtil validatorUtil) {
        this.repository = repository;
        this.validatorUtil = validatorUtil;
    }

    //Создание категории через поля
    @Transactional
    public Chat createMessage(String text, LocalDateTime time, Long sender_id, Long chat_id) {
        if (!StringUtils.hasText(text) || sender_id == null || sender_id <= 0 || chat_id == null || chat_id <= 0 || time == null) {
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
