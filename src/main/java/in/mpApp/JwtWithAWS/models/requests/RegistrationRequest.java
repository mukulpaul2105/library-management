package in.mpApp.JwtWithAWS.models.requests;

import in.mpApp.JwtWithAWS.enums.RoleAuthority;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {

    @NotNull(message = "First Name cannot be null")
    @NotEmpty(message = "First Name cannot be empty")
    @Size(min = 3, max = 100, message = "First Name should be within 3 to 100 characters long")
    private String firstName;

    @NotEmpty(message = "Last Name cannot be empty")
    @Size(min = 3, max = 100, message = "First Name should be within 3 to 100 characters long")
    private String lastName;

    @NotNull(message = "Role cannot be null")
    private RoleAuthority role;

    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 100, message = "Username should be least 3 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password should be least 8 characters")
    private String password;
}
