package socialnetwork.backend.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data

public class EditMessageDto {

    private String messageId;

    private String visitorsId;

    private LocalDateTime editedDate;

    private LocalDateTime sentDate;
}
