package socialnetwork.backend.service.message;

import org.w3c.dom.stylesheets.LinkStyle;
import socialnetwork.backend.dto.request.EditMessageDto;
import socialnetwork.backend.dto.request.MessageDto;
import socialnetwork.backend.exception.GeneralException;
import socialnetwork.backend.exception.MessageNotFoundException;
import socialnetwork.backend.model.message.Message;

import java.util.List;

public interface MessageService {

    void writeMessage(MessageDto messageDto) throws GeneralException;

    Message findMessageById(String id);

    void deleteMessageById(String id) throws MessageNotFoundException;

    List<Message> findAllMessages();

    Message editMessage(EditMessageDto editMessageDto);
}
