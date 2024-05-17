package dmytro.hadiuchko.springboot.dto.shoppingcart;

import dmytro.hadiuchko.springboot.dto.cartitem.CartItemDto;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
