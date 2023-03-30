package client.rest;

import client.service.ChatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
public class ChatsContoller {
    private final ChatService chatService;

    public ChatsContoller(ChatService chatService) {
        this.chatService = chatService;
    }
}
