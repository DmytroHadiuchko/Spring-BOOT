package dmytro.hadiuchko.springboot.service.impl;

import dmytro.hadiuchko.springboot.dto.cartitem.AddCartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.CartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.UpdateCartItemRequestDto;
import dmytro.hadiuchko.springboot.dto.shoppingcart.ShoppingCartDto;
import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.entity.CartItem;
import dmytro.hadiuchko.springboot.entity.ShoppingCart;
import dmytro.hadiuchko.springboot.entity.User;
import dmytro.hadiuchko.springboot.exception.EntityNotFoundException;
import dmytro.hadiuchko.springboot.mapper.CartItemMapper;
import dmytro.hadiuchko.springboot.mapper.ShoppingCartMapper;
import dmytro.hadiuchko.springboot.repository.BookRepository;
import dmytro.hadiuchko.springboot.repository.CartItemRepository;
import dmytro.hadiuchko.springboot.repository.ShoppingCartRepository;
import dmytro.hadiuchko.springboot.service.ShoppingCartService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto getById(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't find ShoppingCart with id: " + userId));
        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        Set<CartItemDto> cartItemDtos = cartItemRepository
                .findByShoppingCartId(shoppingCart.getId())
                .stream()
                .map(cartItemMapper::toDto)
                .collect(Collectors.toSet());
        shoppingCartDto.setCartItems(cartItemDtos);
        return shoppingCartDto;
    }

    @Override
    @Transactional
    public CartItemDto addToCart(AddCartItemDto requestDto, User user) {
        ShoppingCart shoppingCart = getShoppingCartForUser(user.getId());
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(
                       () -> new EntityNotFoundException(
                               "Can't find book by id: " + requestDto.getBookId()));

        CartItem cartItem = cartItemMapper.toEntity(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    @Transactional
    public CartItemDto updateCartItem(UpdateCartItemRequestDto updateDto,
                                      Long userId,
                                      Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCartForUser(userId);
        CartItem cartItem = getCartItem(cartItemId, shoppingCart.getId());
        cartItem.setQuantity(updateDto.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    @Transactional
    public void removeCartItem(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCartForUser(userId);
        CartItem cartItem = getCartItem(cartItemId, shoppingCart.getId());
        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public ShoppingCart createShoppingCartForUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCart getShoppingCartForUser(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by id:" + userId));
    }

    private CartItem getCartItem(Long cartItemId, Long shoppingCartId) {
        return cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCartId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't find cart item in shopping "
                                        + "cart by cart item id:" + cartItemId));
    }
}
