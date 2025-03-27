package deserailizationprocess;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private Integer id;
    private String name;
    private String email;
    private String gender;
    private String status;
}
