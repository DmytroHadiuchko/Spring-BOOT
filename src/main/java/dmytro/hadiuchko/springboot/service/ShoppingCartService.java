package dmytro.hadiuchko.springboot.service;

import dmytro.hadiuchko.springboot.dto.cartitem.AddCartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.UpdateCartItemRequestDto;
import dmytro.hadiuchko.springboot.dto.shoppingcart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto getById(Long id);

    ShoppingCartDto addToCart(AddCartItemDto requestDto);

    ShoppingCartDto updateCartItem(UpdateCartItemRequestDto updateDto, Long userId, Long cartItemId);

    ShoppingCartDto removeCartItem(Long userId, Long cartItemId);
}
