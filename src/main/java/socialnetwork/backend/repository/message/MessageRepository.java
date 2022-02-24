package socialnetwork.backend.repository.message;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import socialnetwork.backend.model.message.Message;

import java.time.LocalDateTime;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    Message findByContentAndSentDate(String context);

    Message findByMessageId(String msgId);

    boolean existsByContent(String context);

    default  void editMessage(Message editedMessage){
        editedMessage.setSentDate(LocalDateTime.now());
        save(editedMessage);
    }
}
