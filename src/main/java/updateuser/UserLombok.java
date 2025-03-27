package updateuser;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserLombok {

    private String name;
    private String email;
    private String gender;
    private String status;
}
