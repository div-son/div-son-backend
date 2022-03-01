package socialnetwork.backend.controller.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialnetwork.backend.model.message.Message;
import socialnetwork.backend.repository.message.MessageRepository;

@RestController
@RequestMapping("/api/send-receive/messages")
@Slf4j

public class MessageController {

    private Message message;

    @Autowired
    MessageRepository messageRepository;

}
