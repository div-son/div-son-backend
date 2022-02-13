package socialnetwork.backend.model.actorProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ActorProfile {

    @Id
    private String id;

    private String firstname;

    private String lastname;

    private String password;

    private String phoneNumber;

    private Boolean isVerified;

    private ActorType actorType;

    private LocalDate modifiedDate;
}
