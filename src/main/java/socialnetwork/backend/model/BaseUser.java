package socialnetwork.backend.model;

import lombok.Data;
import socialnetwork.backend.model.visitor.VisitorType;

@Data
public abstract class BaseUser {

    private String id;

    private String firstName;

    private String lastName;

    private String password;

    private String phoneNumber;

    private VisitorType userType;
}
