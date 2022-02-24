package socialnetwork.backend.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialnetwork.backend.dto.request.EditMessageDto;
import socialnetwork.backend.dto.request.MessageDto;
import socialnetwork.backend.exception.EmptyMessageException;
import socialnetwork.backend.exception.GeneralException;
import socialnetwork.backend.exception.InvalidVisitorException;
import socialnetwork.backend.exception.MessageNotFoundException;
import socialnetwork.backend.model.message.Message;
import socialnetwork.backend.model.visitor.Visitor;
import socialnetwork.backend.repository.message.MessageRepository;
import socialnetwork.backend.repository.visitor.VisitorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class MessageServiceImpl implements MessageService{
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private VisitorRepository visitorRepository;
    
    private Visitor visitor;
    
    @Override
    public void writeMessage(MessageDto messageDto) throws GeneralException {
        
        if (messageDto.getText() == null){
            throw new EmptyMessageException("You can send an empty message.");
        }
        
        if (messageDto.getVisitorsId() == null){
            throw new InvalidVisitorException("Visitor without an id can't send a message.");
        }
        
        Message message = new Message();
        
        message.setSentDate(LocalDateTime.now());
        message.setMessageId(message.getMessageId());
        message.setDeliveredDate(LocalDateTime.now());
        message.setContent(message.getContent());
        message.setVisitorsId(messageDto.getVisitorsId());
        messageRepository.save(message);

    }

    @Override
    public Message findMessageById(String id) {
        return messageRepository.findByMessageId(id);
    }

    @Override
    public void deleteMessageById(String id) throws MessageNotFoundException {
        Message message = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException("A message with that id doesn't exist"));
    }

    @Override
    public List<Message> findAllMessages() {
        return null;
    }

    @Override
    public Message editMessage(EditMessageDto editMessageDto) {
        return null;
    }
}
