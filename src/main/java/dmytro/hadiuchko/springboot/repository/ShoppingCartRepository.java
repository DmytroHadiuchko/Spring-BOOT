package dmytro.hadiuchko.springboot.repository;

import java.util.Optional;
import dmytro.hadiuchko.springboot.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserId(Long userId);
}
