package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.entity.OrderItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

    Optional<OrderItem> findByIdAndOrderId(Long orderItemId, Long orderId);
}
