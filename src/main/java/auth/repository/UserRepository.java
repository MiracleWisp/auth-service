package auth.repository;

import auth.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Long> {

    AppUser findByUsernameIgnoreCase(String username);
}