package dmytro.hadiuchko.springboot.service;

import dmytro.hadiuchko.springboot.dto.cartitem.AddCartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.CartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.UpdateCartItemRequestDto;

public interface CartItemService {
    CartItemDto addCartItem(AddCartItemDto addCartItemDto);

    CartItemDto updateCartItem(UpdateCartItemRequestDto updateDto, Long userId, Long cartItemId);

    void removeCartItem(Long userId, Long cartItemId);

}
