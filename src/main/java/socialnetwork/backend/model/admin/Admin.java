package socialnetwork.backend.model.admin;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import socialnetwork.backend.model.BaseUser;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Admin extends BaseUser {

    private LocalDateTime createdDate;

    private Boolean isActive;

    @DBRef
    private VisitorProfile user;


}
