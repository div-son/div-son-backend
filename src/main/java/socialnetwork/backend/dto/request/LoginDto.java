package socialnetwork.backend.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginDto {

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String password;

    private String type;
}
