package dmytro.hadiuchko.springboot.dto.orderitem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderItemResponseDto {
    @Schema(example = "864", nullable = true)
    private Long id;
    @Schema(example = "225", nullable = true)
    private Long bookId;
    @Schema(example = "5", nullable = true)
    private int quantity;
}
