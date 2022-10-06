package in.mpApp.JwtWithAWS.services;


import in.mpApp.JwtWithAWS.dtos.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserDTO create(final UserDTO userDTO);
    Optional<UserDTO> getByUsername(final String username);
    List<UserDTO> getAll();
}
