package client.rest_mobile;

import client.configuration.WebConfiguration;
import client.data.model.dto.ChatDto;
import client.data.model.dto.MessageDto;
import client.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/chat")
public class ChatsController {
    private final ChatService chatService;

    public ChatsController(ChatService chatService) {
        this.chatService = chatService;
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

//    @PostMapping("/postOne")
//    public String postOneChat(Cha messageDto) {
//        return chatService.createChat(messageDto).toString();
//    }
    // логика сообщений

//    @GetMapping("/getAll/{user_id}")
//    public List<MessageDto> getAllMessages(@PathVariable Long user_id) {
//        // возможность запросить чаты ВСЕ у пользователя
//        // по всем абсолютно его заказам
//        return chatService.findUserChats(user_id).stream().map(ChatDto::new).toList();
//    }
//
//    @GetMapping("/getOneMessage/{message_id}")
//    public MessageDto getMessage(@PathVariable Long message_id) {
//        return chatService.findUserChats(message_id).stream().map(ChatDto::new).toList();
//    }
//
//    @PostMapping("/postOneMessage/{user_id}")
//    public String postOneChat(MessageDto messageDto) {
//        return chatService.(messageDto).toString();
//    }
}
