package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {
}
