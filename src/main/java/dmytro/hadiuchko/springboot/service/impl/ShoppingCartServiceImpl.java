package dmytro.hadiuchko.springboot.service.impl;

import dmytro.hadiuchko.springboot.dto.cartitem.AddCartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.UpdateCartItemRequestDto;
import dmytro.hadiuchko.springboot.dto.shoppingcart.ShoppingCartDto;
import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.entity.CartItems;
import dmytro.hadiuchko.springboot.entity.ShoppingCart;
import dmytro.hadiuchko.springboot.entity.User;
import dmytro.hadiuchko.springboot.exception.BookNotFoundException;
import dmytro.hadiuchko.springboot.mapper.CartItemMapper;
import dmytro.hadiuchko.springboot.mapper.ShoppingCartMapper;
import dmytro.hadiuchko.springboot.repository.BookRepository;
import dmytro.hadiuchko.springboot.repository.CartItemRepository;
import dmytro.hadiuchko.springboot.repository.ShoppingCartRepository;
import dmytro.hadiuchko.springboot.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartDto getById(Long id) {
        return null;
    }

    @Override
    public ShoppingCartDto addToCart(AddCartItemDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.
                findByUserId(requestDto.getUserId()).orElseGet(
                        () -> createNewCart(requestDto.getUserId())
                );
        Book book = bookRepository.findByUserId(requestDto.getBookId()).orElseThrow(
                () -> new BookNotFoundException("Can't find book by id: " + requestDto.getBookId())
        );
        CartItems cartItems = cartItemMapper.toEntity(requestDto, book, shoppingCart);
        shoppingCart.getCartItems().add(cartItems);
        return shoppingCartRepository.save(shoppingCartMapper.toDto(shoppingCart));
    }

    @Override
    public ShoppingCartDto updateCartItem(UpdateCartItemRequestDto updateDto, Long userId, Long cartItemId) {
        return null;
    }

    @Override
    public ShoppingCartDto removeCartItem(Long userId, Long cartItemId) {
        return null;
    }
}
