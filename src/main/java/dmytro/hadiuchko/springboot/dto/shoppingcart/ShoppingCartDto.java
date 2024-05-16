package dmytro.hadiuchko.springboot.dto.shoppingcart;

import dmytro.hadiuchko.springboot.dto.cartitem.CartItemDto;
import lombok.Data;
import java.util.Set;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
