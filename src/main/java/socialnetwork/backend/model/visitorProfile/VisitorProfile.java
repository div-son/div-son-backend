package socialnetwork.backend.model.visitorProfile;

import lombok.*;
import org.springframework.data.annotation.Id;
import socialnetwork.backend.model.visitor.VisitorType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VisitorProfile {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private Boolean isVerified;

    private LocalDateTime modifiedDate;

    private VisitorType userType;
}
