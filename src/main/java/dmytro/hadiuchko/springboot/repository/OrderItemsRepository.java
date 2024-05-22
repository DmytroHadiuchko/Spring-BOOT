package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.entity.OrderItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

    @Query("SELECT oi FROM OrderItem oi JOIN oi.order o WHERE oi.id = "
            + ":orderItemId AND o.user.id = :userId AND o.id = :orderId")
    Optional<OrderItem> findByIdAndUserIdAndOrderId(@Param("orderItemId") Long orderItemId,
                                                    @Param("userId") Long userId,
                                                    @Param("orderId") Long orderId);
}
