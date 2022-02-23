package socialnetwork.backend.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import socialnetwork.backend.model.visitor.Visitor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Message {

    @Id
    private String messageId;

//    @DBRef
    private String visitorsId;

    private String context;

    private LocalDateTime sentDate;

    private LocalDateTime deliveredDate;
}
