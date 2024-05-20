package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);

    List<Order> findOrdersByUserId(Long userId);
}
