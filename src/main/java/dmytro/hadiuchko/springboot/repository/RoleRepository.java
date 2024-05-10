package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.entity.Role;
import dmytro.hadiuchko.springboot.enums.RoleName;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findAllByName(RoleName roleNames);
}
