package client.rest_mobile;

import client.configuration.WebConfiguration;
import client.data.model.dto.ChatDto;
import client.data.model.dto.MessageDto;
import client.service.ChatService;
import client.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/chat")
public class ChatsController {
    private final ChatService chatService;
    private final MessageService messageService;

    public ChatsController(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @GetMapping("/getAll/{user_id}")
    public List<ChatDto> getAllChats(@PathVariable Long user_id) {
        // возможность запросить чаты ВСЕ у пользователя
        // по всем абсолютно его заказам
        return chatService.findUserChats(user_id).stream().map(ChatDto::new).toList();
    }

    @GetMapping("/getOne/{id}")
    public ChatDto getOneChat(@PathVariable Long id) {
        return new ChatDto(chatService.findChat(id));
    }

    @GetMapping("/getAllMessagesByChat/{chat_id}")
    public List<MessageDto> getAllMessages(@PathVariable Long chat_id) {
        return messageService.findAllMessagesByChat(chat_id)
                .stream()
                .map(MessageDto::new)
                .toList();
    }

    @PostMapping("/postOneMessage")
    public MessageDto postOneMessage(@RequestBody MessageDto messageDto) {
        return messageService.addMessage(messageDto);
    }
}
