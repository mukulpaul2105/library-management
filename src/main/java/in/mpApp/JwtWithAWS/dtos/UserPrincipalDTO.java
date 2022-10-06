package in.mpApp.JwtWithAWS.dtos;

import in.mpApp.JwtWithAWS.enums.AccountStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class UserPrincipalDTO implements UserDetails {

    private UserDTO userDTO;

    public UserPrincipalDTO(final UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userDTO.getRole().name()));
    }

    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !AccountStatus.INACTIVE.equals(userDTO.getStatus());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !AccountStatus.SUSPENDED.equals(userDTO.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return AccountStatus.ACTIVE.equals(userDTO.getStatus());
    }
}
