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
    public String getAllChats(@PathVariable Long id) {
        // где возможность запросить чаты ВСЕ у пользователя
        // по всем абсолютно его заказам
        return "";
    }

    @GetMapping("/getOne/{chat_id}")
    public String getOneChat(@PathVariable Long id) {
        return chatService.findChat(id).toString();
    }

    @PostMapping("/postOne/{user_id}")
    public String postOneChat(MessageDto messageDto) {
        return chatService.createMessage(messageDto).toString();
    }
}
