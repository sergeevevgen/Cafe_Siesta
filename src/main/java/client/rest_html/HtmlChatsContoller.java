package client.rest_html;

import client.service.ChatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats_html")
public class HtmlChatsContoller {
    private final ChatService chatService;

    public HtmlChatsContoller(ChatService chatService) {
        this.chatService = chatService;
    }
}
