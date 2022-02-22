package socialnetwork.backend.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResetPasswordDto {

    @NotEmpty
    private String password;

    @NotEmpty
    private String phoneNumber;
}
