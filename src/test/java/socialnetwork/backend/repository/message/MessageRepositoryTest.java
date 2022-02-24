package socialnetwork.backend.repository.message;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import socialnetwork.backend.exception.MessageNotFoundException;
import socialnetwork.backend.model.message.Message;
import socialnetwork.backend.model.visitor.Visitor;
import socialnetwork.backend.repository.visitor.VisitorRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Slf4j

public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    private Message message;

    @Autowired
    private VisitorRepository visitorRepository;

    private Visitor visitor;

    @BeforeEach
    public void initSetUp(){
        message = new Message();
        visitor = new Visitor();
    }

    @Test
    @DisplayName("Write")
    public void test_writeMessage(){

        String visitorsId = "621689ba7be9f32400dcc6b1";

        message.setContent("Hi, I miss you.");
        message.setDeliveredDate(LocalDateTime.now());
        message.setSentDate(LocalDateTime.now());
        message.setVisitorsId(visitorsId);

        messageRepository.save(message);
        Assertions.assertNotNull(message);
        log.info("Message was sent successfully -> {}", message);
    }

    @Test
    @DisplayName("Find message")
    public void test_findMessageById() throws MessageNotFoundException {
        message = messageRepository.findById("621689e34903d6436cf6b545")
                .orElseThrow(() -> new MessageNotFoundException("Message with that id doesn't exist."));
        log.info("Found a message with that id -->{}", message);
    }

    @Test
    public void test_findAllMessages(){
        List<Message> messages = messageRepository.findAll();
        log.info("All messages --> {}", messages);
    }

    @Test
    public void test_editMessage() throws MessageNotFoundException {
        message = messageRepository.findById("621689e34903d6436cf6b545")
                .orElseThrow(() -> new MessageNotFoundException("Message with that id doesn't exist."));

        message.setContent("Sorry, I am breaking up with you.");
        messageRepository.editMessage(message);
        log.info("Edited message successfully --> {}", message);
    }
    @Test
    public void test_deleteMessage() throws MessageNotFoundException {
        message = messageRepository.findById("621689e34903d6436cf6b545").
                orElseThrow(() -> new MessageNotFoundException("Message with that id does not exist"));

        messageRepository.deleteById(message.getMessageId());
        log.info("Deleted message successfully --> {}", message);
    }
}
