package dmytro.hadiuchko.springboot.service;

import dmytro.hadiuchko.springboot.dto.cartitem.AddCartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.CartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.UpdateCartItemRequestDto;
import dmytro.hadiuchko.springboot.dto.shoppingcart.ShoppingCartDto;
import dmytro.hadiuchko.springboot.entity.ShoppingCart;
import dmytro.hadiuchko.springboot.entity.User;

public interface ShoppingCartService {
    ShoppingCartDto getById(Long id);

    ShoppingCartDto addToCart(AddCartItemDto requestDto, User user);

    CartItemDto updateCartItem(UpdateCartItemRequestDto updateDto, Long userId, Long cartItemId);

    void removeCartItem(Long userId, Long cartItemId);

    ShoppingCart createShoppingCart(User user);
}
