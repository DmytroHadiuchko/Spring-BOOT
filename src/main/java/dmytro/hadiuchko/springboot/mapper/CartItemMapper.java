package dmytro.hadiuchko.springboot.mapper;

import dmytro.hadiuchko.springboot.config.MapperConfig;
import dmytro.hadiuchko.springboot.dto.cartitem.AddCartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.CartItemDto;
import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.entity.CartItems;
import dmytro.hadiuchko.springboot.entity.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    CartItemDto toDto(CartItems cartItems);

    CartItems toEntity(AddCartItemDto cartItemDto);

    default CartItems toEntity(AddCartItemDto cartItemDto, Book book, ShoppingCart shoppingCart) {
        CartItems cartItems = toEntity(cartItemDto);
        cartItems.setShoppingCart(shoppingCart);
        cartItems.setBook(book);
        return cartItems;
    }
}
