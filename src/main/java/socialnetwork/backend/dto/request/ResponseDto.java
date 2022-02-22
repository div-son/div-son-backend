package socialnetwork.backend.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import socialnetwork.backend.model.visitor.Gender;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ResponseDto {

    private String id;

    private String token;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private Boolean isVerified;

    private LocalDateTime createdDate;

    private Gender sex;

    private String verificationToken;

    private Boolean isActive;

    private LocalDateTime modifiedDate;

}
