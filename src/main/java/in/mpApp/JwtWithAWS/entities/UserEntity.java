package in.mpApp.JwtWithAWS.entities;

import in.mpApp.JwtWithAWS.constants.EntityName;
import in.mpApp.JwtWithAWS.constants.TableName;
import in.mpApp.JwtWithAWS.enums.AccountStatus;
import in.mpApp.JwtWithAWS.enums.RoleAuthority;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = EntityName.USER)
@Table(name = TableName.USER)
public class UserEntity extends IDedEntity {

    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(100)")
    private String lastName;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleAuthority role;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(name = "username", nullable = false, columnDefinition = "VARCHAR(100)", unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;


}
