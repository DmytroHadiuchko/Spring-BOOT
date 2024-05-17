package dmytro.hadiuchko.springboot.dto.cartitem;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateCartItemRequestDto {
    @PositiveOrZero(message = "Quantity must be zero or a positive number")
    private int quantity;
}
