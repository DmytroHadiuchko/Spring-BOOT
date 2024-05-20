package dmytro.hadiuchko.springboot.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCartItemRequestDto {
    @NotNull
    @Positive(message = "Quantity must be more than 0")
    private int quantity;
}
