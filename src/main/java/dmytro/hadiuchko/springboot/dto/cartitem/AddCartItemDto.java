package dmytro.hadiuchko.springboot.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class AddCartItemDto {
    @NotNull(message = "Book ID cannot be null")
    private Long bookId;
    @PositiveOrZero(message = "Quantity must be zero or a positive number")
    private int quantity;
}
