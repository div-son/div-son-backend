package socialnetwork.backend.model.actor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import socialnetwork.backend.model.actorProfile.ActorProfile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Actor {

    @Id
    private String id;

    private String email;

    @DBRef
    private ActorProfile actorProfile;

    private String dateOfBirth;

    private String address;

    private LocalDate createdDate;

    private Gender sex;
}
