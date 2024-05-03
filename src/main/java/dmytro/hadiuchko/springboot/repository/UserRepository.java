package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findAllByEmail(String email);
}
