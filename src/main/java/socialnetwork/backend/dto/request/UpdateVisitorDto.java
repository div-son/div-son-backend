package socialnetwork.backend.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateVisitorDto {

    @NotNull
    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String gender;

    private String email;

    private String dateOfBirth;
}
