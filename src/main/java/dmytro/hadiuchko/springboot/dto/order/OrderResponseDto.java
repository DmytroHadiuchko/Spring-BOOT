package dmytro.hadiuchko.springboot.dto.order;

import dmytro.hadiuchko.springboot.dto.orderitem.OrderItemResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponseDto {
    @Schema(example = "568", nullable = true)
    private Long id;
    @Schema(example = "681", nullable = true)
    private Long userId;
    @Schema(nullable = true)
    private List<OrderItemResponseDto> orderItems;
    @Schema(nullable = true)
    private LocalDateTime orderDate;
    @Schema(example = "165.28", nullable = true)
    private BigDecimal total;
    @Schema(example = "DELIVERED", nullable = true)
    private String status;
}
