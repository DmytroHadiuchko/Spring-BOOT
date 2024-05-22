package dmytro.hadiuchko.springboot.dto.order;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequestDto(
        @NotBlank(message = "Shipping address may not be empty")
        String shippingAddress
) {
}
