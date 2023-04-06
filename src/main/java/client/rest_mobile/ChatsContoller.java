package client.rest_mobile;

import client.data.model.dto.MessageDto;
import client.service.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chats")
public class ChatsContoller {
    private final ChatService chatService;

    public ChatsContoller(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/getAll/{user_id}")
    public String getAllChats(@PathVariable Long user_id) {
        // возможность запросить чаты ВСЕ у пользователя
        // по всем абсолютно его заказам
        return chatService.findUserChats(user_id).toString();
    }

    @GetMapping("/getOne/{chat_id}")
    public String getOneChat(@PathVariable Long id) {
        return chatService.findChat(id).toString();
    }

//    @PostMapping("/postOne")
//    public String postOneChat(Cha messageDto) {
//        return chatService.createChat(messageDto).toString();
//    }
}
