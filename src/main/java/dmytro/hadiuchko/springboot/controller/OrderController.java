package dmytro.hadiuchko.springboot.controller;

import dmytro.hadiuchko.springboot.dto.order.CreateOrderRequestDto;
import dmytro.hadiuchko.springboot.dto.order.OrderResponseDto;
import dmytro.hadiuchko.springboot.dto.order.UpdateOrderStatusRequestDto;
import dmytro.hadiuchko.springboot.dto.orderitem.OrderItemResponseDto;
import dmytro.hadiuchko.springboot.entity.User;
import dmytro.hadiuchko.springboot.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create new order", description = "Create new order")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public OrderResponseDto createOrder(@Valid @RequestBody CreateOrderRequestDto requestDto,
                                        @AuthenticationPrincipal User user) {
        return orderService.createOrder(requestDto, user.getId());
    }

    @Operation(summary = "Update status", description = "Looking for order by id and update status")
    @PutMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public OrderResponseDto updateStatus(@Valid @RequestBody
                                             UpdateOrderStatusRequestDto requestDto,
                                         @PathVariable Long orderId,
                                         @AuthenticationPrincipal User user) {
        return orderService.updateOrder(user.getId(), orderId, requestDto);
    }

    @Operation(summary = "Get all orders",
            description = "Returns list with user's orders")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<OrderResponseDto> getAllOrders(@AuthenticationPrincipal User user) {
        return orderService.getAllOrdersForUserById(user.getId());
    }

    @Operation(summary = "Get all orders by id",
            description = "Return list with user's orders by id")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{orderId}/items")
    public List<OrderItemResponseDto> getAllOrdersById(@PathVariable Long orderId,
                                                 @AuthenticationPrincipal User user) {
        return orderService.getOrderItemsByOrdersId(orderId, user.getId());
    }

    @Operation(summary = "Get order item", description = "Returns order item")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto getOrderItem(@PathVariable Long orderId,
                                             @PathVariable Long itemId,
                                             @AuthenticationPrincipal User user) {
        return orderService.getOrderItemById(orderId, user.getId(), itemId);
    }
}
