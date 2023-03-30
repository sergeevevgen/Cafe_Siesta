package client.rest_html;

import client.service.ChatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats_html")
public class ChatsContoller {
    private final ChatService chatService;

    public ChatsContoller(ChatService chatService) {
        this.chatService = chatService;
    }
}
