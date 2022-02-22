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

    private String lastName;

    @Pattern(regexp = "^[0]\\d{10}$", message = "Invalid phone number")
    @Digits(integer = 11, fraction = 0)
    @Size(min = 11, max = 11, message = "Invalid phone number")
    private String phoneNumber;

    private String password;
}
