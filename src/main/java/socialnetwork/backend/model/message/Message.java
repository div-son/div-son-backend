package socialnetwork.backend.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Message {

    @Id
    private String messageId;

    private String visitorsId;

    private String content;

    private LocalDateTime sentDate;

    private LocalDateTime deliveredDate;
}
