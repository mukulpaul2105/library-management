package in.mpApp.JwtWithAWS.models.responses;

import in.mpApp.JwtWithAWS.enums.AccountStatus;
import in.mpApp.JwtWithAWS.enums.RoleAuthority;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserCreatedResponse extends IDedResponse {

    private String firstName;
    private String lastName;
    private RoleAuthority role;
    private AccountStatus status;
    private String username;
}
