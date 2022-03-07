package socialnetwork.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
//@Builder
@NoArgsConstructor

public class RegisterVisitorDto {

    @NotEmpty(message = "First name is required")
    private String firstName;

<<<<<<< HEAD
    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "email is required")
=======
    @Size(min = 2, max = 20, message = "Last name should be 2 to 20 character long")
    private String lastName;

    @Pattern(regexp = "^[@]\\s")
>>>>>>> a727f09 (removed message controller)
    private String email;

    @NotEmpty(message = "password is should not be lesser than 5 characters")
    private String password;
}
