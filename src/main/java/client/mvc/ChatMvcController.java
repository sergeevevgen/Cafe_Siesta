package client.mvc;

import client.service.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatMvcController {
    private final ChatService chatService;

    public ChatMvcController(ChatService chatService) {
        this.chatService = chatService;
    }
}
