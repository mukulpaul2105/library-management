package in.mpApp.JwtWithAWS.dtos;

import in.mpApp.JwtWithAWS.enums.AccountStatus;
import in.mpApp.JwtWithAWS.enums.RoleAuthority;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDTO extends IDedDTO {

    private String firstName;
    private String lastName;
    private RoleAuthority role;
    private AccountStatus status;
    private String username;
    private String password;
}
