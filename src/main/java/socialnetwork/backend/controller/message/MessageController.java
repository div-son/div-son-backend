package socialnetwork.backend.controller.message;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialnetwork.backend.dto.request.WriteMessageDto;
import socialnetwork.backend.model.message.Message;
import socialnetwork.backend.repository.message.MessageRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/messages")
@Slf4j

public class MessageController {

    private Message message;

    @Autowired
    MessageRepository messageRepository;

    ModelMapper modelMapper = new ModelMapper();


    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody WriteMessageDto writeMessageDto){
        if (writeMessageDto.get)
    }

}
