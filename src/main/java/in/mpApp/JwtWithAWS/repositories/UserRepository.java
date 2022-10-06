package in.mpApp.JwtWithAWS.repositories;

import in.mpApp.JwtWithAWS.entities.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends IDedRepository<UserEntity> {

    Optional<UserEntity> findByUsername(final String username);
}
