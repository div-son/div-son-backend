package socialnetwork.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MessageDto {

 private String text;

 private LocalDateTime createdDate;

 private String visitorsId;


}
