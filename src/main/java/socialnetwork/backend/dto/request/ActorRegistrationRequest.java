package socialnetwork.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class ActorRegistrationRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String password;
}