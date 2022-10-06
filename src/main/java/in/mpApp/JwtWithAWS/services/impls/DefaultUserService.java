package in.mpApp.JwtWithAWS.services.impls;

import in.mpApp.JwtWithAWS.dtos.UserDTO;
import in.mpApp.JwtWithAWS.entities.UserEntity;
import in.mpApp.JwtWithAWS.enums.AccountStatus;
import in.mpApp.JwtWithAWS.repositories.UserRepository;
import in.mpApp.JwtWithAWS.services.IUserService;
import in.mpApp.JwtWithAWS.utils.DataMapperUtil;
import in.mpApp.JwtWithAWS.utils.LoggedInContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DefaultUserService implements IUserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO create(UserDTO userDTO) {
        log.info("Creating User DTO: {} ", userDTO.getUsername());
        UserEntity userEntity = DataMapperUtil.convertTo(userDTO, UserEntity.class);
        userEntity.setCreatedBy(LoggedInContext.getCurrentUser());
        userEntity.setCreatedOn(LocalDateTime.now());
        userEntity.setStatus(AccountStatus.ACTIVE);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity = userRepository.save(userEntity);
        log.info("Saved User Details in DB: {} ", userEntity);
        return DataMapperUtil.convertTo(userEntity, UserDTO.class);
    }

    @Override
    public Optional<UserDTO> getByUsername(String username) {
        log.info("Fetching User Details by Username: {} ", username);
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        log.info("Fetched User Details by Username: {} ",username, " " , userEntity);
        return Optional.of(DataMapperUtil.convertTo(userEntity, UserDTO.class));
    }

    @Override
    public List<UserDTO> getAll() {
        log.info("Fetching all Users: {} ");
        List<UserDTO> userDTOList = new ArrayList<>();
        userRepository.findAll().forEach((ue) -> {
            userDTOList.add(DataMapperUtil.convertTo(ue, UserDTO.class));
        });
        return userDTOList;
    }
}
