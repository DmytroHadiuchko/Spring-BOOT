package dmytro.hadiuchko.springboot.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddCartItemDto {
    @NotNull(message = "Book ID cannot be null")
    private Long bookId;
    @NotNull(message = "Quantity may not be null")
    @Positive(message = "Quantity must be more than 0")
    private int quantity;
}
