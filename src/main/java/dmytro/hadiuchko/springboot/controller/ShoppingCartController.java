package dmytro.hadiuchko.springboot.controller;

import dmytro.hadiuchko.springboot.dto.cartitem.AddCartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.CartItemDto;
import dmytro.hadiuchko.springboot.dto.cartitem.UpdateCartItemRequestDto;
import dmytro.hadiuchko.springboot.dto.shoppingcart.ShoppingCartDto;
import dmytro.hadiuchko.springboot.entity.User;
import dmytro.hadiuchko.springboot.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart management", description = "Endpoints for managing shopping carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    @Operation(summary = "Add book to shopping cart", description = "Add book to shopping cart")
    public CartItemDto addBookToShoppingCart(
            @RequestBody @Valid AddCartItemDto addCartItemDto,@AuthenticationPrincipal User user) {
        return shoppingCartService.addToCart(addCartItemDto, user);
    }

    @Operation(summary = "Get shopping cart", description = "Looking for user's shopping cart")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ShoppingCartDto getShoppingCart(@AuthenticationPrincipal User user) {
        return shoppingCartService.getById(user.getId());
    }

    @Operation(summary = "Update quantity", description = "Update book's quantity in shopping cart")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/cart-items/{cartItemsId}")
    public CartItemDto updateQuantity(@RequestBody @Valid UpdateCartItemRequestDto updateDto,
                                      @AuthenticationPrincipal User user,
                                      @PathVariable Long cartItemsId) {
        return shoppingCartService.updateCartItem(updateDto, user.getId(), cartItemsId);
    }

    @Operation(summary = "delete", description = "delete from shopping cart")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/cart-item/{cartItemsId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cartItemsId, @AuthenticationPrincipal User user) {
        shoppingCartService.removeCartItem(user.getId(), cartItemsId);
    }
}
