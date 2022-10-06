package in.mpApp.JwtWithAWS.repositories;

import in.mpApp.JwtWithAWS.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface IDedRepository<T> extends JpaRepository<T, Long> {

}
