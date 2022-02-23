package socialnetwork.backend.repository.message;

import org.springframework.data.mongodb.repository.MongoRepository;
import socialnetwork.backend.model.message.Message;

import java.time.LocalDateTime;

public interface MessageRepository extends MongoRepository<Message, String> {

    Message findByContextAndSentDate(String context);

    Message findByMessageId(String msgId);

    boolean existsByContext(String context);

    default  void editMessage(Message editedMessage){
        editedMessage.setSentDate(LocalDateTime.now());
        save(editedMessage);
    }
}
