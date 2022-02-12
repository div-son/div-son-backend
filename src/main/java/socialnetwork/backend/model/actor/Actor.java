package socialnetwork.backend.model.actor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Actor {

    @Id
    private String id;

    private String email;
}
