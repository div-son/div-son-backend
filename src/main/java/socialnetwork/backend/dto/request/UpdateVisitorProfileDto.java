package socialnetwork.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import socialnetwork.backend.model.visitor.VisitorType;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateVisitorProfileDto {

    @NonNull
    private String id;

    private String firstName;

    private String lastName;

    private VisitorType userType;
}
