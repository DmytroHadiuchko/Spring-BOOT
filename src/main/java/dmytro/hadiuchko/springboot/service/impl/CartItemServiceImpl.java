package dmytro.hadiuchko.springboot.service.impl;

import dmytro.hadiuchko.springboot.dto.cartitem.AddCartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.CartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.UpdateCartItemRequestDto;
import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.entity.CartItems;
import dmytro.hadiuchko.springboot.entity.ShoppingCart;
import dmytro.hadiuchko.springboot.entity.User;
import dmytro.hadiuchko.springboot.exception.BookNotFoundException;
import dmytro.hadiuchko.springboot.exception.EntityNotFoundException;
import dmytro.hadiuchko.springboot.mapper.CartItemMapper;
import dmytro.hadiuchko.springboot.repository.BookRepository;
import dmytro.hadiuchko.springboot.repository.CartItemRepository;
import dmytro.hadiuchko.springboot.repository.ShoppingCartRepository;
import dmytro.hadiuchko.springboot.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemDto addCartItem(AddCartItemDto addCartDto) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(addCartDto.getUserId())
                .orElseGet(() -> createNewCart(addCartDto.getUserId()));
        Book book = bookRepository.findById(addCartDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: "
                        + addCartDto.getBookId()));
        CartItems cartItems = cartItemMapper.toEntity(addCartDto, book, shoppingCart);
        shoppingCart.getCartItems().add(cartItems);
        shoppingCartRepository.save(shoppingCart);
        return cartItemMapper.toDto(cartItems);
    }

    @Override
    public CartItemDto updateCartItem(UpdateCartItemRequestDto updateDto,
                                      Long userId,
                                      Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by user id: " + userId));
        CartItems cartItems = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));
        cartItems.setQuantity(updateDto.getQuantity());
        cartItemRepository.save(cartItems);
        return cartItemMapper.toDto(cartItems);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) {

    }

    private ShoppingCart createNewCart(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(new User(userId));
        return shoppingCartRepository.save(shoppingCart);
    }
}
