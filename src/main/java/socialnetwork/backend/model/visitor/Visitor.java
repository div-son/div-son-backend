package socialnetwork.backend.model.visitor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Visitor{

    @Id
    private String id;

    @DBRef
    private VisitorProfile user;

    private String email;

    private String verificationToken;

    @NonNull
    private LocalDateTime createdDate;

    private Gender gender;

    private String dateOfBirth;

    @NonNull
    private Boolean isActive;

    @NonNull
    private LocalDateTime modifiedDate;
}