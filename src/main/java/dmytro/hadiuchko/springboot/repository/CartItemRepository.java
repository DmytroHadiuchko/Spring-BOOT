package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.entity.CartItem;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Set<CartItem> findByShoppingCartId(Long id);

    Optional<CartItem> findByIdAndShoppingCartId(Long cartItemId, Long shoppingCartId);
}
