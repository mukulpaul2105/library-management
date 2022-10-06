package in.mpApp.JwtWithAWS.models.responses;

import in.mpApp.JwtWithAWS.enums.AccountStatus;
import in.mpApp.JwtWithAWS.enums.RoleAuthority;
import lombok.Data;

@Data
public class RegisteredResponse {

    private String firstName;
    private String lastName;
    private RoleAuthority role;
    private AccountStatus status;
    private String username;

}
