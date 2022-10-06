package in.mpApp.JwtWithAWS.services;

import in.mpApp.JwtWithAWS.dtos.UserDTO;
import in.mpApp.JwtWithAWS.dtos.UserPrincipalDTO;
import in.mpApp.JwtWithAWS.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserPrincipalService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Fetching User by username in UserPrincipalService: {} ");
        final UserDTO userDTO = userService.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with the username " + username + " not found"));
        log.info("Retrieved User: {} ", userDTO);
        return new UserPrincipalDTO(userDTO);
    }
}
